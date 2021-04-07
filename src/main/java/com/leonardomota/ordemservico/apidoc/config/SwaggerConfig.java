package com.leonardomota.ordemservico.apidoc.config;

import java.time.LocalDateTime;
import java.util.function.Predicate;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
					.apis(RequestHandlerSelectors.any())
					.paths(apiPaths())
				.build()
				.directModelSubstitute(LocalDateTime.class, java.util.Date.class);
	}

	private Predicate<String> apiPaths() {
		return PathSelectors.regex("/ordemservico.*")
				.or(PathSelectors.regex("/clientes.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Api Ordem de Serviço!")
				.description("\"API desenvolvida para o teste seleção lyncas, utilizando java 11 e banco h2.\"")
				.version("0.1.0")
				.contact(new Contact("Leonardo Mota", "https://github.com/leonardomota", ""))
				.build();
	}
}
