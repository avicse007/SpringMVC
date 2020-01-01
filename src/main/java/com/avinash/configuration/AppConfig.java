package com.avinash.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.avinash.convertors.StringToEnumConvertor;

@Configuration
@ComponentScan(basePackages="com.avinash")
public class AppConfig  extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("css/**", "images/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/images/");
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }
    
    @Override
    protected void addFormatters(FormatterRegistry registry) {
    	registry.addConverter( new StringToEnumConvertor());
    	super.addFormatters(registry);
    }
    
    /* To support async processing 
     * 
     *      
    */
    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {
    	asyncSupportConfigurer.setDefaultTimeout(5000);
    	//this set method needs an instance of AsyncTaskExecutor 
    	// so we will get it via a Bean mvcTaskExecutor
    	asyncSupportConfigurer.setTaskExecutor(mvcTaskExecutor());
    }
    
    //Bean for returning the AsyncTaskExecutor
    @Bean
    public AsyncTaskExecutor mvcTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setThreadNamePrefix("hplus-thread-");
    	return taskExecutor;
    }
}