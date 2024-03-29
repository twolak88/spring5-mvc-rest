/**
 * 
 */
package com.twolak.springframework.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author twolak
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport{

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(metaData())
				.tags(
						new Tag("VendorController", "This is my Vendor Controller"),
						new Tag("CategoryController", "This is my Category Controller"));
	}
	
	private ApiInfo metaData() {
		Contact contact = new Contact("Tomasz Wolak", "https://spring.com.pl", "twolak88@gmail.com");
		return new ApiInfo("Rest spring framework", 
				"Spring framework udemy", 
				"1.0", 
				"Terms of service url", 
				contact, 
				"Apache 2.0 License", 
				"https://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
