package net.abadguy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderMainZK80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainZK80.class,args);
    }
}
