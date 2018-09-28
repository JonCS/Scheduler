package com.jw.meetingscheduler.config;

import java.util.Collections;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()   
          .apiInfo(apiInfo())
        	  .genericModelSubstitutes(Optional.class);
    }
	
	private ApiInfo apiInfo() {
	     return new ApiInfo(
	       "Meeting Scheduler REST API", 
	       "Backend APIs for the Meeting Scheduler Application", 
	       "API TOS", 
	       "Terms of service", 
	       new Contact("Alex Liu", "localhost:8080", "alexliu16@gmail.com"), 
	       "License of API", "API license URL", Collections.emptyList());
	}
}
