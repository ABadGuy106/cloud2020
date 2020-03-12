package net.abadguy.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import net.abadguy.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/hystrix/payment/ok/{id}")
    public String paymentInfoOk(@PathVariable("id") Integer id){
        return paymentService.paymentInfoOk(id);
    }

    @GetMapping("/hystrix/payment/timeout/{id}")
    public String paymentInfoTime(@PathVariable("id") Integer id){
        return paymentService.paymentInfoTime(id);
    }


    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitHystrix(@PathVariable("id") Integer id){
        String s = paymentService.paymentCircuitBreaker(id);
        log.info("结果");
        return s;
    }


}
