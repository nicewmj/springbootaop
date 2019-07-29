package com.springboot.demo;

import com.springboot.demo.service.Refund;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Resource
    private Refund refund;

    @Test
    public void contextLoads() {
    }


    @Test
    public void testRefund() {

        Integer refund = this.refund.getRefund();
        System.out.println(refund);

    }

}
