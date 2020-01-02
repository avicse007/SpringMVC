package com.avinash.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;

import com.avinash.convertors.StringToEnumConvertor;
import com.avinash.interceptors.LoggingInterceptor;

@Configuration
@ComponentScan(basePackages="com.avinash")
public class AppConfig  extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("css/**", "images/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/images/");
    }

    //Internal view resolver
    
    @Bean
    public InternalResourceViewResolver jspViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        //for chaining of the view resolver we can set the order
        viewResolver.setOrder(2);
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }
    
    
  //for xml view resolver
    @Bean
    public XmlViewResolver xmlViewResolver() {
    	XmlViewResolver viewResolver = new XmlViewResolver();
    	viewResolver.setLocation(new ClassPathResource("view.xml"));
    	//for chaining of the view resolver we can set the order
        viewResolver.setOrder(1);
    	return viewResolver;
    }
    
  //for ResourceBundle View Resolver
    /*

    @Bean
    public ResourceBundleViewResolver resourceBundleViewResolver() {
    	ResourceBundleViewResolver viewResolver = new ResourceBundleViewResolver();
    	viewResolver.setBasename("views");
    	return viewResolver;
    } 
     * 
     */    
    
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
    
    //Override a method to register the Custom interceptor that we have created 
    
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/*");
    }
    
}