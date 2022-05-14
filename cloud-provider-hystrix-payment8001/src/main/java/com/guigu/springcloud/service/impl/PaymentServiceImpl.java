package com.guigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.guigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    public String getPaymentInfo_OK(Integer id) {
        return Thread.currentThread().getName()+"----OK----"+id;
    }

    //@HystrixCommand:启用hystrix
    @HystrixCommand(fallbackMethod = "getPaymentInfo_ErrorHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String getPaymentInfo_Error(Integer id) {
//        return Thread.currentThread().getName()+"----ERROR----"+id;
        System.out.println(Thread.currentThread().getName()+"-----------error----------"+id);
        Integer timeout = 5;
        try {
            int a = 10 / 0;
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName()+"-----getPaymentInfo_Error----ERROR----"+id;
    }


    public String getPaymentInfo_ErrorHandler(Integer id) {
        return Thread.currentThread().getName()+"-----------getPaymentInfo_ErrorHandler----ERROR----"+id;
    }
//---服务的熔断
    @HystrixCommand(
            fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), //时间窗口期(时间范围)
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少后跳闸

    }
    //上述配置可以理解为在10s的时间范围内，10次请求的失败率达到60%，就执行跳闸操作 断路器状态close->open
    )
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id<0) {
            throw new RuntimeException("******id不能为负数");
        }
        /**
         * hutool工具包中的相关工具类
         */
        String simpleUUID = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t" + "成功调用，流水号是：" + simpleUUID;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id不能为负数，请稍后再试............"+id;
    }


}
