package com.wegrow.wegrow.demo.service.imlp;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.demo.bo.DemoUserDetails;
import com.wegrow.wegrow.demo.dao.NameIdMapDao;
import com.wegrow.wegrow.demo.dao.UserLocalAuthDao;
import com.wegrow.wegrow.demo.dto.UserInfoUpdateParam;
import com.wegrow.wegrow.demo.service.UserService;
import com.wegrow.wegrow.demo.dto.UpdateUserPasswordParam;
import com.wegrow.wegrow.demo.dto.UserParam;
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
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

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

    @Autowired
    private NameIdMapDao nameIdMapDao;

    @Override
    public User getUserByUsername(String username) {
        // example用来构建复杂的SQL，然后使用model mapper进行查询
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExampleWithBLOBs(userExample);
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
        userMapper.insertSelective(user);
        LocalAuth localAuth = new LocalAuth();

        // mybatis插入用户之后，会自动给对象的ID进行赋值
        localAuth.setUserId(user.getId());
        localAuth.setPassword(passwordEncoder.encode(userParam.getPassword()));

        // 给创建的用户添加默认的普通用户角色
        RolesExample rolesExample = new RolesExample();
        rolesExample.createCriteria().andNameEqualTo("ORDINARY_USER");
        ArrayList<Integer> roles_list = new ArrayList<>();
        roles_list.add(rolesMapper.selectByExample(rolesExample).get(0).getId());
        localAuthMapper.insertSelective(localAuth);
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
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
            example.or(example.createCriteria().andEmailLike("%" + keyword + "%"));
        }
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectByExample(example);
    }

    @Override
    public String update(String principalName, UserInfoUpdateParam userInfoUpdateParam) {
        Integer userId = nameIdMapDao.getId(userInfoUpdateParam.getUsername());
        if (!principalName.equals(userInfoUpdateParam.getUsername()) && userId != null) {
            return null;
        }
        User user = new User();
        user.setId(nameIdMapDao.getId(principalName));
        BeanUtils.copyProperties(userInfoUpdateParam, user);
        userMapper.updateByPrimaryKeySelective(user);
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        return jwtTokenUtil.generateToken(userDetails);
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
    public int updatePassword(String principalName, UpdateUserPasswordParam updatePasswordParam) {
        if (StrUtil.isEmpty(updatePasswordParam.getNewPassword())) {
            return -1;
        }

        LocalAuth localAuth = this.getLocalAuth(nameIdMapDao.getId(principalName));
        if (null == localAuth) {
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

    @Override
    public HashMap<String, String> getSummaryUserInfo(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return null;
        } else {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("userName", user.getUsername());
            hashMap.put("aboutMe", user.getAboutMe());
            hashMap.put("avatarHash", user.getAvatarHash());
            hashMap.put("userId", user.getId().toString());
            return hashMap;
        }
    }
}
