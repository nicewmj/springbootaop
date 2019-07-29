package com.springboot.demo.annotion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyTarget {
    String hello();
    int[] array() default {1,2,3,4};
    

}
