package net.abadguy.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {

    //自定义ribbon路由策略
    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
}
