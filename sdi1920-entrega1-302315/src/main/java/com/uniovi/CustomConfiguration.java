package com.uniovi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class CustomConfiguration implements WebMvcConfigurer {
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		Properties prop = new Properties();
		try {
			FileInputStream file = new FileInputStream("src/main/resources/config.properties");
			
			prop.load(file);
		
			int page = Integer.parseInt(prop.getProperty("spring.data.web.pageable.page-parameter"));
			int size = Integer.parseInt(prop.getProperty("spring.data.web.pageable.default-page-size"));
			PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
			resolver.setFallbackPageable(PageRequest.of(page, size));
			argumentResolvers.add(resolver);
			
		} catch (FileNotFoundException e) {
			System.out.println("### ERROR: config.properties not found ###");
		} catch (IOException e) {
			System.out.println("### ERROR: failed trying to load config.properties ###");
		}
	}
}