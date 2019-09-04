package com.canto;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p></p>
 *
 * @author iqunqunqun
 * @className AdminApplication
 * @since 2019/8/27 22:55
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.canto.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
