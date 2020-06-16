package com.wegrow.wegrow.demo.bo;

import com.wegrow.wegrow.model.LocalAuth;
import com.wegrow.wegrow.model.Permission;
import com.wegrow.wegrow.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DemoUserDetails implements UserDetails {

    private final User user;

    private final List<Permission> permissionList;

    private final LocalAuth localAuth;

    public DemoUserDetails(User user, List<Permission> permissionList, LocalAuth localAuth) {
        this.user = user;
        this.permissionList = permissionList;
        this.localAuth = localAuth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissionList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return localAuth.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return permissionList.isEmpty();
    }
}
