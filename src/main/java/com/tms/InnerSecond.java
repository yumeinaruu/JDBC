package com.tms;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Primary  //то же самое что квалифаер только легче, но тогда в любой интерфейсной ссылке будет бин этого класса, что не круто
@Component
public class InnerSecond implements InnerInterface{
}
