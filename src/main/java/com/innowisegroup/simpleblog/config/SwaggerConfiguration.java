package com.innowisegroup.simpleblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        List<Predicate<String>> basePath = new ArrayList<>();
        basePath.add(PathSelectors.ant("/simpleblog/users*"));
        basePath.add(PathSelectors.ant("/simpleblog/users/**"));
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.innowisegroup.simpleblog.controller"))
                .paths(basePath.stream().reduce(Predicate::or).orElse(PathSelectors.any()))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Simple Blog API")
                .description("Simple blog application API with only one controller and DTO for users")
                .contact(new Contact("Vera", null, "shavela99@gmail.com"))
                .version("1.0")
                .build();
    }
}
