package net.abadguy.controller;

import cn.hutool.core.util.IdUtil;
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
public class PaymentHystrixController {

    @Resource
    HystrixService hystrixService;
    @GetMapping("/consumer/hystrix/payment/ok/{id}")
    public String paymentInfoOk(@PathVariable("id") Integer id){
        return hystrixService.paymentInfoOk(id);
    }

    @GetMapping("/consumer/hystrix/payment/timeout/{id}")
    @HystrixCommand(fallbackMethod = "pyamentTimeOutFallbackMethod",commandProperties = {
            //设置该方法的超时时间
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfoTime(@PathVariable("id") Integer id){
        return hystrixService.paymentInfoTime(id);
    }


    public String pyamentTimeOutFallbackMethod(@PathVariable("id") Integer id){
        return "我是消费者80，对方支付系统繁忙";
    }




}
