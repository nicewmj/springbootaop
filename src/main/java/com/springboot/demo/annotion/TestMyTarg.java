package com.springboot.demo.annotion;

import java.lang.reflect.Method;

public class TestMyTarg {

    public static void main(String[] args) throws Exception{
        TestMyTest testMyTest =new TestMyTest();
        Class<? extends TestMyTest> aClass = testMyTest.getClass();
        Method method = aClass.getMethod("output",new Class[] {});
        //如果TestMyTest类名上有注解@MyAnnotation修饰，则为true
        if(TestMyTest.class.isAnnotationPresent(MyTarget.class)){
            System.out.println("我有这个注解......");
        }
        if(method.isAnnotationPresent(MyTarget.class)){
            //调用output方法
            method.invoke(testMyTest,null);
            //获取方法上注解@MyAnnotation的信息
            MyTarget annotation = method.getAnnotation(MyTarget.class);
            String hello = annotation.hello();
            System.out.println("hello   "+hello);
            int[] array = annotation.array();
            System.out.println("array  "+array);

        }
    }
}
