package com.demo.TaskManager.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Autorise toutes les routes
            .allowedOrigins("http://localhost:4200") // Frontend Angular
            .allowedMethods("*") // Méthodes HTTP autorisées
            .allowedHeaders("*") // Tous les headers sont autorisés
            .allowCredentials(true); // Permet les cookies
    }
}

