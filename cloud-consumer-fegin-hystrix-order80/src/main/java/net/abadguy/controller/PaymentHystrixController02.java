package net.abadguy.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import net.abadguy.service.HystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "globalFallback")//配置全局降级方法
public class PaymentHystrixController02 {

    @Resource
    HystrixService hystrixService;
    @GetMapping("/new/consumer/hystrix/payment/ok/{id}")
    public String paymentInfoOk(@PathVariable("id") Integer id){
        return hystrixService.paymentInfoOk(id);
    }

    @GetMapping("/new/consumer/hystrix/payment/timeout/{id}")
    @HystrixCommand//开启方法降级注解
    public String paymentInfoTime(@PathVariable("id") Integer id){
        return hystrixService.paymentInfoTime(id);
    }


    public String globalFallback(){
        return "new我是消费者80，对方支付系统繁忙";
    }
}
