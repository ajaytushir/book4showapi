package org.catalystone.bookurshow.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer  {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200");
	}

	@Bean
	public CorsFilter corsFilter() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList("GET", "POST"));
		config.setAllowCredentials(true); // you USUALLY want this
		// config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//		 configurer.setDefaultTimeout(360000).setTaskExecutor(getAsyncExecutor());
//         configurer.registerCallableInterceptors(callableProcessingInterceptor());
//         //configurer.registerCallableInterceptors(callableProcessingInterceptor());
//         WebMvcConfigurer.super.configureAsyncSupport(configurer);
		 ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
         t.setCorePoolSize(10);
         t.setMaxPoolSize(100);
         t.setQueueCapacity(50);
         t.setAllowCoreThreadTimeOut(true);
         t.setKeepAliveSeconds(120);
         t.initialize();
         configurer.setTaskExecutor(t);
	}
	
	/*@Override
	@Bean(name = "taskExecutor")
	public AsyncTaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		return executor;
	}


	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	@Bean
	public CallableProcessingInterceptor callableProcessingInterceptor() {
		return new TimeoutCallableProcessingInterceptor() {
			@Override
			public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
				return super.handleTimeout(request, task);
			}
		};
	}*/
}
