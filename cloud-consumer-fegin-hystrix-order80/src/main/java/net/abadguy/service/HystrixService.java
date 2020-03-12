package net.abadguy.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE",fallback = HystrixFallbackService.class)
public interface HystrixService {
    @GetMapping("/hystrix/payment/ok/{id}")
    public String paymentInfoOk(@PathVariable("id") Integer id);

    @GetMapping("/hystrix/payment/timeout/{id}")
    public String paymentInfoTime(@PathVariable("id") Integer id);
}
