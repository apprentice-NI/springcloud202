package com.guigu.springcloud.controller;


import com.guigu.springcloud.dataobjects.CommonResult;
import com.guigu.springcloud.dataobjects.Payment;
import com.guigu.springcloud.lb.LoadBalance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

//cloud-consumer-Order80是服务的消费端，要引用cloud-provider-payment8001微服务的提供的功能
//实现订单微服务调用支付微服务，使用restTemplate实现80端口到8001端口的调用
@RestController
public class OrderController {
//    public static final String PAYMENT_URL = "http://localhost:8001";
    /**
     * 这里指定的是服务提供集群对外暴露的微服务名称CLOUD-PAYMENT-SERVICE
     */
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    /**
     * @Resource注解和@Autowired注解的区别：
     * 1：@Resource注解是javax支持的注解，无需spring的开发环境即可使用
     * 2：@Resource的注入方式为byName，@Autowired的注入方式是byType
     */
    @Resource
    public RestTemplate restTemplate;

    @Resource
    public LoadBalance loadBalance;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        /**
         * 通过restTemplate调用cloud-provider-payment8001 controller中定义的 http：//local host：8001/payment/create接口
         */
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommonResult<Payment> getEntityById(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        /**
         * getForEntity相比于getForObject(返回json)，包含了响应中的一些重要信息，比如响应头，响应状态码，响应体等
         */
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444,"操作失败");
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB(){

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if(instances == null || instances.size() <= 0){
            return null;
        }else{
            ServiceInstance instance = loadBalance.instances(instances);

            URI uri = instance.getUri();

            return restTemplate.getForObject(uri + "/payment/lb",String.class);
        }
    }
}
