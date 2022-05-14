package com.guigu.springcloud.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderController {

    private static final String RPC_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("consumer/consul")
    public String consulConsumer(){

        return restTemplate.getForObject(RPC_URL + "/payment/consul",String.class);
    }
}
