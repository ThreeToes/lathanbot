package com.stephengream.main;

import com.stephengream.lathanbot.services.IrcService;
import com.stephengream.lathanbot.services.PircBotIrcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
@ComponentScan(basePackages = {"com.stephengream.lathanbot.controllers"})
@PropertySource("classpath:/com/stephengream/lathanbot/config/default.properties")
public class Application {
    
    @Bean
    public IrcService ircService(){
        return new PircBotIrcService();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
        (new Thread(ctx.getBean(IrcService.class))).start();
        System.out.println("AFTER THE BOT CONNECT!");
    }

}