package com.tms;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class Cap {
    public String text;

    /*@Autowired*/ //1. Через поле(худший способ)
/*    @Qualifier("innerSecond")
    @Autowired*/
    private InnerInterface inner;

    @Autowired  //2. через конструктор(лучший)
    public Cap(@Qualifier("innerSecond") InnerInterface inner) {
        this.inner = inner;
    }

/*    @Autowired //3. через сеттер
    public void setInner(Inner inner) {
        this.inner = inner;
    }*/

    @Override
    public String toString() {
        return "Cap{" +
                "text='" + text + '\'' +
                ", inner=" + inner +
                '}';
    }


    @PostConstruct
    public void initMethod(){
        System.out.println("Hello Cap Bean");
    }

    @PreDestroy //работает только если скоуп синглтон и контекст закрыт
    public void destroyMethod(){
        System.out.println("Bye Cap Bean");
    }
}
