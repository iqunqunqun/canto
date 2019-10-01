package com.canto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canto.entity.UserBase;
import com.canto.service.IUserBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p></p>
 *
 * @author iqunqunqun
 * @className UserDetailServiceImpl
 * @since 2019/9/28 23:03
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserBaseService userBaseService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UserBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", username);
        UserBase one = this.userBaseService.getOne(queryWrapper);
        if (one == null) {
            System.out.println("不存在此用户");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("4567");

        return new User(Objects.requireNonNull(one).getMobile(), result, authorities);
    }
}
