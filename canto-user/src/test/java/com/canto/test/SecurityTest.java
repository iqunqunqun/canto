package com.canto.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author iqunqunqun
 * @className Test
 * @since 2019/9/15 15:55
 */


@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityTest {

    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    @Autowired
    private UserDetailsService detailsService;

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    public void securityTest() throws Exception {


        AuthenticationManager am = new SampleAuthenticationManager();

        int flag = 0;

        while (flag < 4) {
            System.out.println("Please enter your username:");
            String name = "ivan";
            System.out.println("Please enter your password:");
            String password = "ivan";
            flag += 1;
            try {
                Authentication request = new UsernamePasswordAuthenticationToken(name, password);
                Authentication result = am.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);


                break;
            } catch (AuthenticationException e) {
                System.out.println("Authentication failed: " + e.getMessage());
            }
        }
        System.out.println("Successfully authenticated. Security context contains: " +
                SecurityContextHolder.getContext().getAuthentication());


    }

    private static class SampleAuthenticationManager implements AuthenticationManager {
        public Authentication authenticate(Authentication auth) throws AuthenticationException {
            if (auth.getName().equals(auth.getCredentials())) {
                return new UsernamePasswordAuthenticationToken(auth.getName(),
                        auth.getCredentials(), AUTHORITIES);
            }
            throw new BadCredentialsException("Bad Credentials");
        }
    }
}
