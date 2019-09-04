package com.canto.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>自定义负载均衡策略</p>
 *
 * @author iqunqunqun
 * @className RibbonConfig
 * @since 2019/8/31 16:58
 */

@Configuration
public class RibbonConfig {

    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
}
