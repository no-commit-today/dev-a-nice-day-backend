package com.nocommittoday.techswipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechSwipeBatchApplication {

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(TechSwipeBatchApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}
