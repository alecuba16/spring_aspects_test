package com.alecuba16.service;

import com.alecuba16.aspect.Countable;
import com.alecuba16.aspect.Loggable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Value("${app.greeting}")
    private String greeting;

    public GreetingService(){
        super();
    }

    @Loggable
    @Countable
    public String getGreeting(String name){
        return greeting + " " + name;
    }
}
