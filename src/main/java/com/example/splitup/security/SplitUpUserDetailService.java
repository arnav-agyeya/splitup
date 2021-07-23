package com.example.splitup.security;

import com.example.splitup.dao.ILoginUserInfoDAO;
import com.example.splitup.entities.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SplitUpUserDetailService implements UserDetailsService {

    @Autowired
    private ILoginUserInfoDAO loginUserInfoDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LoginUserInfo userInfoByUserName = loginUserInfoDAO.getLoginUserInfoByUserName(userName);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("User");
        return new MyUserDetails(new ArrayList<>() {{
            add(authority);
        }}, userInfoByUserName.getPassWord(), userName, true);
    }

    private class MyUserDetails implements UserDetails {

        private List<? extends GrantedAuthority> authorities;
        private String password;
        private String userName;
        private boolean isAccountNonExpired;

        public MyUserDetails(List<? extends GrantedAuthority> authorities, String password, String userName, boolean isAccountNonExpired) {
            this.authorities = authorities;
            this.password = password;
            this.userName = userName;
            this.isAccountNonExpired = isAccountNonExpired;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return userName;
        }

        @Override
        public boolean isAccountNonExpired() {
            return isAccountNonExpired;
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
            return true;
        }
    }
}
