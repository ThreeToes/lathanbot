package com.stephengream.main;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author stephen
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args){
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        System.out.println("Beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for(String bean : beanNames){
            System.out.println(bean);
        }
    }
}
