package com.alura.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPI30
public class SpringdocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contacto = new Contact();
        contacto.setEmail("davidhb944@gmail.com");
        contacto.setName("David Herrera");
        contacto.setUrl("https://www.linkedin.com/in/brayan-david-herrera-acero-7878b9232?lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_view_base_contact_details%3B%2B0SeH9%2BqTlGeHSd2A4RWvg%3D%3D");

        OpenAPI api = new OpenAPI();
        api.info(new Info()
                .title("Foro Alura")
                .version("1.0.0")
                .description("Resolución del challenge 4: Foro Alura del ProgramaONE de Oracle Next Educacion")
                .contact(contacto));
        api.components(new Components()
                .addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                                .bearerFormat("JWT")));
        return api;
    }

}