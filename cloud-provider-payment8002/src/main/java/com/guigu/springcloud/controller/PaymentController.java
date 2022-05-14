package com.guigu.springcloud.controller;

import com.guigu.springcloud.dataobjects.CommonResult;
import com.guigu.springcloud.dataobjects.Payment;
import com.guigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    //写操作对应这个
    @PostMapping(value = "/payment/create")
    /**
     * @RequestBody注解是用来接收前端给后端传递的json字符串的
     * @RequestBody与@RequestParam()可以同时使用，@RequestBody最多只能有一个，而@RequestParam()可以有多个。
     * @ResponseBody：用指定的格式将一个方法的返回值加载到response的body区域
     */
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);

        if(result > 0){
            return new CommonResult(200,"插入数据成功 serverPort = " + serverPort,result);
        }else{
            return new CommonResult(444,"插入数据失败",null);
        }

    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.queryById(id);
        if(payment != null){
            return new CommonResult(200,"通过id查询对象成功 serverPort = " + serverPort,payment);
        }else{
            return new CommonResult(444,"通过id查询对象失败",null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }
}
