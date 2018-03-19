package com.enginizer.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${app.swagger.enabled}")
    private boolean isSwaggerEnabled;

    public static final Contact DEFAULT_CONTACT = new Contact(
            "Dragos Triteanu",
            "http://redabacus.eu",
            "dragos.triteanu@redabacus.eu");

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SPRING_WEB)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enginizer.rest")).build()
                .enable(isSwaggerEnabled)
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Enginizer API")
                .description("Docs for the Enginizer API template")
                .version("1.0")
                .contact(DEFAULT_CONTACT)
                .build();
    }

}

