package com.tms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cap {
    public String text;

    @Autowired //1. Через поле(худший способ)
    public Inner inner;


}
