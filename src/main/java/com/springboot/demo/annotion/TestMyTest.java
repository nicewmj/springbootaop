package com.springboot.demo.annotion;

import java.lang.reflect.Method;


@MyTarget(hello = "你好")
public class TestMyTest {

    @MyTarget(hello = "hello",array = {1,2})
    public void doSomething(){
        System.out.println("doSomething  say hello");
    }

    @MyTarget(hello = "你好",array = {12,24})
    @Deprecated
    @SuppressWarnings("")
    public void output()
    {
        System.out.println("output something!");
    }


    public static void main(String[] args) throws Exception {
        Method method = TestMyTest.class.getMethod("doSomething", null);
        if(method.isAnnotationPresent(MyTarget.class)){//如果doSomething方法上存在注解@MyTarget，则为true
            System.out.println("存在 "+method.getAnnotation(MyTarget.class));

        }

    }
}
