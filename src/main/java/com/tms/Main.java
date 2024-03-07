package com.tms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan("com.tms") //спринг будет вычитывать все классы через рефлексию начиная с того пакета где лежит этот класс(Main)
//можно указать откуда вычитывать(в данном случае com.tms)
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Cap capBean = (Cap) context.getBean("cap");
        System.out.println(capBean.inner);
    }
}
