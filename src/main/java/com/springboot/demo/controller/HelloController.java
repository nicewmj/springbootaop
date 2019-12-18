package com.springboot.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        //下面是测试控制针异常
        String s = null;
        if (s.equals("a")){
            System.out.println("------测试空指针的");
        }
        return "hello word 你好";
    }

    /**
     * 切面获取参数 id
     * @param id
     * @return
     */
    @RequestMapping("/say")
    public String say(@RequestParam String id){
        return "战神 "+id;
    }
}

