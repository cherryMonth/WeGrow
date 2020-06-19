package com.wegrow.wegrow.demo.service.imlp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.demo.bo.DemoUserDetails;
import com.wegrow.wegrow.demo.dao.UserLocalAuthDao;
import com.wegrow.wegrow.demo.service.DemoAdminService;
import com.wegrow.wegrow.demo.service.UpdateUserPasswordParam;
import com.wegrow.wegrow.demo.service.UserParam;
import com.wegrow.wegrow.mapper.LocalAuthMapper;
import com.wegrow.wegrow.mapper.RolesMapper;
import com.wegrow.wegrow.mapper.UserMapper;
import com.wegrow.wegrow.mapper.UserRoleMapMapper;
import com.wegrow.wegrow.model.*;
import com.wegrow.wegrow.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.wegrow.wegrow.demo.dao.UserRoleMapDao;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DemoAdminServiceImpl implements DemoAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoAdminServiceImpl.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private LocalAuthMapper localAuthMapper;

    @Autowired
    private UserRoleMapMapper userRoleMapMapper;

    @Autowired
    private UserLocalAuthDao userLocalAuthDao;

    @Autowired
    private UserRoleMapDao userRoleMapDao;

    @Override
    public User getUserByUsername(String username) {
        // example用来构建复杂的SQL，然后使用model mapper进行查询
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User register(UserParam userParam) {
        User user = new User();
        // 从表格参数中向Bean赋值
        BeanUtils.copyProperties(userParam, user);
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(user.getUsername());
        userExample.or().andEmailEqualTo(user.getEmail());
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() > 0) {
            return null;
        }
        userMapper.insert(user);
        LocalAuth localAuth = new LocalAuth();

        // mybatis插入用户之后，会自动给对象的ID进行赋值
        localAuth.setUserId(user.getId());
        localAuth.setPassword(passwordEncoder.encode(userParam.getPassword()));

        // 给创建的用户添加默认的普通用户角色
        RolesExample rolesExample = new RolesExample();
        rolesExample.createCriteria().andNameEqualTo("ORDINARY_USER");
        ArrayList<Integer> roles_list = new ArrayList<>();
        roles_list.add(rolesMapper.selectByExample(rolesExample).get(0).getId());
        localAuthMapper.insert(localAuth);
        updateRole(user.getId(), roles_list);
        return user;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            updateLoginTimeByUsername(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public User getItem(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
            example.or(example.createCriteria().andEmailLike("%" + keyword + "%"));
        }
        return userMapper.selectByExample(example);
    }

    @Override
    public int update(Integer id, User user) {
        user.setId(id);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int delete(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateRole(Integer userId, List<Integer> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        UserRoleMapExample userRoleMapExample = new UserRoleMapExample();
        userRoleMapExample.createCriteria().andUserIdEqualTo(userId);
        userRoleMapMapper.deleteByExample(userRoleMapExample);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UserRoleMap> list = new ArrayList<>();
            for (Integer roleId : roleIds) {
                UserRoleMap userRoleMap = new UserRoleMap();
                userRoleMap.setUserId(userId);
                userRoleMap.setRoleId(roleId);
                list.add(userRoleMap);
            }
            userRoleMapDao.insertList(list);
        }
        return count;
    }


    @Override
    public LocalAuth getLocalAuth(Integer userId) {
        return userLocalAuthDao.getLocalAuth(userId);
    }

    @Override
    public List<Roles> getRolesList(Integer userId) {
        return userRoleMapDao.getRolesList(userId);
    }

    @Override
    public List<Permission> getPermissionList(Integer userId) {
        return userRoleMapDao.getPermissionList(userId);
    }

    @Override
    public int updatePassword(UpdateUserPasswordParam updatePasswordParam) {
        if (StrUtil.isEmpty(updatePasswordParam.getEmail())
                || StrUtil.isEmpty(updatePasswordParam.getOldPassword())
                || StrUtil.isEmpty(updatePasswordParam.getNewPassword())) {
            return -1;
        }

        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(updatePasswordParam.getEmail());
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollUtil.isEmpty(userList)) {
            return -2;
        }
        LocalAuth localAuth = this.getLocalAuth(userList.get(0).getId());
        if (!passwordEncoder.matches(updatePasswordParam.getOldPassword(), localAuth.getPassword())) {
            return -3;
        }
        localAuth.setPassword(passwordEncoder.encode(updatePasswordParam.getNewPassword()));
        localAuthMapper.updateByPrimaryKey(localAuth);
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            LocalAuth localAuth = this.getLocalAuth(user.getId());
            List<Permission> permissionList = getPermissionList(user.getId());
            return new DemoUserDetails(user, permissionList, localAuth);
        }
        throw new UsernameNotFoundException("用户名或者密码错误");
    }

    @Override
    public void updateLoginTimeByUsername(String username) {
        User record = new User();
        record.setLastSeen(new Date());
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        userMapper.updateByExampleSelective(record, example);
    }
}
