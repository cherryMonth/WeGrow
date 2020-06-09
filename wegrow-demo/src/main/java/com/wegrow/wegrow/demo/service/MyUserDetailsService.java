package com.wegrow.wegrow.demo.service;

import com.wegrow.wegrow.demo.entity.User;
import com.wegrow.wegrow.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
UserDetails是我们自行设计系统的用户、账户的概念。他包含了用户名、密码及其对应的授予权限。
UserDetails就是当前系统中如何获取库存用户信息的服务。
我们添加@Service注解，就是为了使Spring自动配置
 */

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override // Security需要一个实现了UserDetailsService Bean作为用户信息查询
    // 我们可以使用JdbcUserDetailsManager进行查询(也实现了UserDetailsService)，但是这样要求表结构需要按照要求设置
    // 为了扩大自由度，我们只需要实现UserDetailsService接口即可，需要覆盖loadUserByUsername方法，给定用户名返回用户信息
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库尝试读取该用户
        User user = userMapper.findByUserName(username);
        // 用户不存在，抛出异常
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 将数据库形式的roles解析为UserDetails的权限集
        // AuthorityUtils.commaSeparatedStringToAuthorityList是Spring Security
        //提供的用于将逗号隔开的权限集字符串切割成可用权限对象列表的方法
        // 当然也可以自己实现，如用分号来隔开等，参考generateAuthorities
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
        return user;
    }
}
