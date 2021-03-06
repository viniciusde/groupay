package com.groupay.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket groupayAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.groupay"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("GROUPAY API REST")
                .description("Movile Hack by Team 9 <br/>"  +
                "Caio Cardozo <br/>" +
                "Carlos Denarde <br/>" +
                "Fabio Rapanelo <br/>" + 
                "Pedro Guilherme Alves <br/>" + 
                "Thiago Barros <br/>")
                .version("v0.1.0")
                .build();

        return apiInfo;
    }
}
