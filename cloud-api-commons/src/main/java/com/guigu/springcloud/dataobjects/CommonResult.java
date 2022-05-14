package com.guigu.springcloud.dataobjects;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//由于子工程pom中依赖了lombok
//以下注解的意思分别是：给对应字段添加getter，setter方法
//全参的构造方法，空参的构造方法
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    //后端返回给前端的状态码
    private int code;
    //描述信息
    private String Message;
    //后端返回给前端T类型的对象数据
    private T data;

    public CommonResult(int code,String message){
        //调用生成的全参的构造方法
        this(code,message,null);
    }
}
