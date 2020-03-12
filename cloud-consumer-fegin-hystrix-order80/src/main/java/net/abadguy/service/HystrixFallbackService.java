package net.abadguy.service;

import org.springframework.stereotype.Component;

@Component
public class HystrixFallbackService implements  HystrixService{
    @Override
    public String paymentInfoOk(Integer id) {
        return "错误";
    }

    @Override
    public String paymentInfoTime(Integer id) {
        return "错误";
    }
}
