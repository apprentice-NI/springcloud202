package com.guigu.springcloud.controller;

import com.guigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("order")
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping("info/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String res = paymentHystrixService.paymentInfo_OK(id);
        System.out.println(res);
        return res;
    };

    @GetMapping("info/error/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_ERROR_Handler",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    public String paymentInfo_ERROR(@PathVariable("id") Integer id){
        int a = 10 / 0;
        return paymentHystrixService.paymentInfo_ERROR(id);
    }

    public String paymentInfo_ERROR_Handler(@PathVariable("id") Integer id){
        return "80____paymentInfo_ERROR_Handler___异常返回";
    }

    //下面是全局fallback方法
    public String payment_Global_FallbackMethod(){
        return "Global 对方繁忙或已经宕机，请稍后重试";
    }
}
