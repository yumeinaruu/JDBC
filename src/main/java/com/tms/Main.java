package com.tms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("MySettings.xml");
        Cap capBean = (Cap) context.getBean("cap");
        System.out.println(capBean.hashCode());


    }
}
