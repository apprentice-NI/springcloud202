package com.guigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalance {

    private AtomicInteger atomicInteger = new AtomicInteger(0);


    public final int getAndIncrement(){
        int current;
        int next;

        do{
            current = this.atomicInteger.get();
            /**
             * 防止int类型的current越界
             */
            next = current >= 2147483647 ? 0 : current + 1;
        }while(!atomicInteger.compareAndSet(current,next));

        System.out.println("*****第几次访问，次数next:" + next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {

        int index = getAndIncrement() % serviceInstances.size();

        return serviceInstances.get(index);
    }
}
