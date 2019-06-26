package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class SpringDataRestCustomization implements RepositoryRestConfigurer {
    @Autowired
    private Environment env;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.getCorsRegistry()
                .addMapping("/**")
                .allowedOrigins(env.getProperty("cors.allowedOrigins",""))
                .allowedHeaders(env.getProperty("cors.allowedHeaders","*"))
                .allowedMethods(env.getProperty("cors.allowedMethods","*"))
                .allowCredentials(true)
                .maxAge(3600);
    }
}
