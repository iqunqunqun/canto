package com.canto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.canto.entity.UserBase;
import com.canto.service.IUserBaseService;
import com.canto.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserBaseService userBaseService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String result = null;
        // 获取用户名
        QueryWrapper<UserBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", username);
        UserBase one = this.userBaseService.getOne(queryWrapper);
        if (one == null) {
            log.error("不存在此用户");
        }

        // 设置权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 设置验证码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // redis获取验证码
        String code = (String) redisUtil.get(Objects.requireNonNull(one).getMobile());
        if (code != null) {
            result = encoder.encode(code);
        }
        return new User(Objects.requireNonNull(one).getMobile(), Objects.requireNonNull(result), authorities);
    }
}
