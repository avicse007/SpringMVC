# Spring MVC 

A web based framework based on MVC design pattern

Arthitecture of Spring MVC
==========================

 ## 1. DispatcherServlet

The DispatcherServlet is an actual Servlet (it inherits from the HttpServlet base class), and as such is declared in the web.xml of your web application. You need to map requests that you want the DispatcherServlet to handle, by using a URL mapping in the same web.xml file. 

 Following is the sequence of events corresponding to an incoming HTTP request to DispatcherServlet −

 #### 1. After receiving an HTTP request, DispatcherServlet consults the HandlerMapping to call the appropriate Controller.

 #### 2. The Controller takes the request and calls the appropriate service methods based on used GET or POST method. The service method will set model data based on defined business logic and returns view name to the DispatcherServlet.

 #### 3. The DispatcherServlet will take help from ViewResolver to pickup the defined view for the request.

 #### 4. Once view is finalized, The DispatcherServlet passes the model data to the view which is finally rendered on the browser.

### 2. WebApplicationInitializer

In a Servlet 3.0+ environment, you also have the option of configuring the Servlet container programmatically.WebApplicationInitializer is an interface provided by Spring MVC that ensures your code-based configuration is detected and automatically used to initialize any Servlet 3 container. An abstract base class implementation of this interace named AbstractDispatcherServletInitializer makes it even easier to register the DispatcherServlet by simply specifying its servlet mapping.

## 3. ApplicationContext

ApplicationContext instances in Spring can be scoped. In the Web MVC framework, each DispatcherServlet has its own WebApplicationContext, which inherits all the beans already defined in the root WebApplicationContext. These inherited beans can be overridden in the servlet-specific scope, and you can define new scope-specific beans local to a given Servlet instance.

 ## 4. WebApplicationContext

The WebApplicationContext is an extension of the plain ApplicationContext that has some extra features necessary for web applications. It differs from a normal ApplicationContext in that it is capable of resolving themes and that it knows which Servlet it is associated with (by having a link to the ServletContext). The WebApplicationContext is bound in the ServletContext, and by using static methods on the RequestContextUtils class you can always look up the WebApplicationContext if you need access to it.




| Annotation                        | Description
|-------------------------------    |--------------
| HandlerMapping                    | Maps incoming requests to handlers and a list of pre- and 
|                                   | post-processors (handler interceptors) based on some 
|                                   | criteria the details of which vary by HandlerMapping 
|                                   | implementation. The most popular implementation supports 
|                                   | annotated controllers but other implementations exists as 
|                                   | well
|   	                              |
| HandlerAdapter   	                | Helps the DispatcherServlet to invoke a handler mapped to a 
|                                   | request regardless of the handler is actually invoked. For 
|                                   | example, invoking an annotated controller requires
|                                   | resolving various annotations. Thus the main purpose of a 
|                                   | HandlerAdapter is to shield the DispatcherServlet from such 
|                                   | details.
|  	                                | 
| HandlerExceptionResolver   	      | Maps exceptions to views also allowing for more complex 
|                                   | exception handling code.
|                                   |
| ViewResolver                      | Resolves logical String-based view names to actual View 
|                                   | types.
| LocaleResolver                    | Resolves the locale a client is using, in order to be able 
|                                   | to offer internationalized views
|   	                              | 
|   	                              |
| ThemeResolver   	                | Resolves themes your web application can use, for example, 
|                                   | to offer personalized layouts 
|  	                                |
| MultipartResolver                 | Parses multi-part requests for example to support processing 
|                                   | file uploads from HTML forms
|                                   |
| FlashMapManager    	              | Stores and retrieves the "input" and the "output" FlashMap 
|                                   | that can be used to pass attributes from one request to 
|                                   | another, usually across a redirect.
|  	                                |

 ## Default DispatcherServlet Configuration

As mentioned in the previous section for each special bean the DispatcherServlet maintains a list of implementations to use by default. This information is kept in the file DispatcherServlet.properties in the package org.springframework.web.servlet.
All special beans have some reasonable defaults of their own. Sooner or later though you'll need to customize one or more of the properties these beans provide. For example it's quite common to configure an InternalResourceViewResolver settings its prefix property to the parent location of view files.

Regardless of the details, the important concept to understand here is that once you configure a special bean such as an InternalResourceViewResolver in your WebApplicationContext, you effectively override the list of default implementations that would have been used otherwise for that special bean type. For example if you configure an InternalResourceViewResolver, the default list of ViewResolver implementations is ignored.

 ## DispatcherServlet Processing Sequence
After you set up a DispatcherServlet, and a request comes in for that specific DispatcherServlet, the DispatcherServlet starts processing the request as follows:

The WebApplicationContext is searched for and bound in the request as an attribute that the controller and other elements in the process can use. It is bound by default under the key DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE.

The locale resolver is bound to the request to enable elements in the process to resolve the locale to use when processing the request (rendering the view, preparing data, and so on). If you do not need locale resolving, you do not need it.

The theme resolver is bound to the request to let elements such as views determine which theme to use. If you do not use themes, you can ignore it.

If you specify a multipart file resolver, the request is inspected for multiparts; if multiparts are found, the request is wrapped in a MultipartHttpServletRequest for further processing by other elements in the process. See Section 17.10, “Spring's multipart (file upload) support” for further information about multipart handling.

An appropriate handler is searched for. If a handler is found, the execution chain associated with the handler (preprocessors, postprocessors, and controllers) is executed in order to prepare a model or rendering.

If a model is returned, the view is rendered. If no model is returned, (may be due to a preprocessor or postprocessor intercepting the request, perhaps for security reasons), no view is rendered, because the request could already have been fulfilled.

Handler exception resolvers that are declared in the WebApplicationContext pick up exceptions that are thrown during processing of the request. Using these exception resolvers allows you to define custom behaviors to address exceptions.

The Spring DispatcherServlet also supports the return of the last-modification-date, as specified by the Servlet API. The process of determining the last modification date for a specific request is straightforward: the DispatcherServlet looks up an appropriate handler mapping and tests whether the handler that is found implements the LastModified interface. If so, the value of the long getLcontextClassastModified(request)   method of the LastModified interface is returned to the client.

You can customize individual DispatcherServlet instances by adding Servlet initialization parameters (init-param elements) to the Servlet declaration in the web.xml

| Parameter                         | Explanation
|-------------------------------    |--------------
| contextClass                      | Class that implements WebApplicationContext, which 
|                                   | instantiates the context used by this Servlet. By default, 
|                                   | the XmlWebApplicationContext is used.
|   	                              |
| contextConfigLocation   	        | String that is passed to the context instance (specified by 
|                                   | contextClass) to indicate where context(s) can be found.  
|                                   | The string consists potentially of multiple strings (
|                                   | using a comma as a delimiter) to support multiple 
|                                   | contexts. In case of multiple context locations with 
|                                   | beans that are defined twice, the latest location takes 
|                                   | precedence.
| namespace                         | Namespace of the WebApplicationContext. Defaults to [
|                                   |  servlet-name]-servlet.

 ## CORE SPRING ANNOTATIONS
 
 ### Context Configuration Annotations
 
 #### 1. Annotation : @Autowired 
 Use : Constructor, Field, Method
 Description : Declares a constructor, field, setter method, or configuration method to be autowired by type. Items annotated with @Autowired do not have to be public.
 
 #### 2. Annotation : @Configurable 
 Use : Type 
 Description : Used with <context:springconfigured> to declare types whose properties should be injected, even if they are not instantiated by Spring. Typically used to inject the properties of domain objects.

#### 3. Annotation : @Order 
Use : Type, Method, Field 
Description : Defines ordering, as an alternative to implementing the org.springframework.core.Ordered interface.

#### 4. Annotation : @Qualifier 
Use : Field, Parameter, Type, Annotation Type
Description : Guides autowiring to be performed by means other than by type.

#### 5. Annotation : @Required 
Use : Method (setters) 
Description : Specifies that a particular property must be injected or else the
configuration will fail.

#### 6. Annotation : @Scope 
Use :  Type 
Description : Specifies the scope of a bean, either singleton, prototype, request, session,
or some custom scope


 ### Stereotyping Annotations

#### 1. Annotation : @Component
Use : Type
Description : Type

#### 2. Annotation : @Controller 
Use : Type 
Description : Stereotypes a component as a Spring MVC controller.

#### 3. Annotation : @Repository 
Use : Type 
Description : Stereotypes a component as a repository. Also indicates that SQLExceptions thrown from the component’s methods should be translated into Spring DataAccessExceptions.
#### 4. Annotation : @Service
Use : Type 
Description : Stereotypes a component as a service.

 ### Spring MVC Annotations

#### 1. Annotation : @Controller 
Use : Type 
Description : Stereotypes a component as a Spring MVC controller.

#### 2. Annotation :  @InitBinder 
Use : Method 
Description : Annotates a method that customizes data binding.

#### 3. Annotation :  @ModelAttribute 
Use : Parameter,Method
Description : When applied to a method, used to preload the model with the value returned from the method. When applied to a parameter, binds a model attribute to the parameter.

#### 4. Annotation : @RequestMapping 
Use : Method,Type
Description : Maps a URL pattern and/or HTTP method to a method or controller type.

#### 5. Annotation : @RequestParam
Use : Parameter
Description : Binds a request parameter to a method parameter.

#### 6. Annotation : @SessionAttributes
Use : Type 
Description : Specifies that a model attribute should be stored in the session.

 ### Transaction Annotations

#### 1. Annotation : @Transactional 
Use : Method, Type 
Description : Declares transactional boundaries and rules on a bean and/or its methods.

 ### JMX Annotations

#### 1. Annotation : @ManagedAttribute 
Use : Method 
Description : Used on a setter or getter method to indicate that the bean’s property should be exposed as a MBean attribute.

#### 2. Annotation : @ManagedNotification 
Use : Type
Description : Indicates a JMX notification emitted by a bean.

#### 3. Annotation : @ManagedNotifications 
Use : Type 
Description : Indicates the JMX notifications emitted by a bean.

#### 4. Annotation : @ManagedOperation 
Use : Method 
Description : Specifies that a method should be exposed as a MBean operation.

#### 5. Annotation : @ManagedOperationParameter 
Use : Method 
Description : Used to provide a description for an operation parameter.

#### 5. Annotation : @ManagedOperationParameters 
Use : Method
Description : Provides descriptions for one or more operation parameters.

#### 6. Annotation : @ManagedResource
Use : Type 
Description : Specifies that all instances of a class should be exposed a MBeans. 


 ### ASPECTJ ANNOTATIONS
 
 #### 1. Annotation : @Aspect 
 Use : Type
 Description : Declares a class to be an aspect.
 
 #### 2. Annotation :  @After 
 Use : Method 
 Description : Declares a method to be called after a pointcut completes.

 #### 3. Annotation : @AfterReturning
 Use : Method
 Description : Declares a method to be called after a pointcut returns successfully.

 #### 4. Annotation : @AfterThrowing 
 Use :Method 
 Description : Declares a method to be called after a pointcut throws an exception.

 #### 5. Annotation : @Around
 Use : Method 
 Description : Declares a method that will wrap the pointcut.

 #### 6. Annotation : @Before 
 Use : Method 
 Description : Declares a method to be called before proceeding to the pointcut.

 #### 7. Annotation :  @DeclareParents
 Use: Static Field 
 Description : Declares that matching types should be given new parents—that is, it introduces new
functionality into matching types.

 #### 8. Annotation :   @Pointcut
 Use : Method 
 Description : Declares an empty method as a pointcut placeholder method

  ### JSR-250 ANNOTATIONS

  #### 1. Annotation : @PostConstruct
  Use : Method
  Description :  Indicates a method to be invoked after a bean has been created and dependency
injection is complete. Used to perform any initialization work necessary.

 #### 2. Annotation :  @PreDestroy 
Use : Method 
Description : Indicates a method to be invoked just before a bean is removed from the Spring
context. Used to perform any cleanup work necessary.

 #### 3. Annotation : @Resource 
 Use : Method, Field 
 Description : Indicates that a method or field should be injected with a named resource (by default, another bean).


 ### TESTING ANNOTATIONS

 #### 1. Annotation : @AfterTransaction
 Use : Method
 Description : Used to identify a method to be invoked after a transaction has completed.
 
 #### 2. Annotation :  @BeforeTransaction
 Use:  Method 
 Description : Used to identify a method to be invoked before a transaction starts.

 #### 3. Annotation :  @ContextConfiguration
 Use : Type
 Description : Configures a Spring application context for a test.
  
 #### 4. Annotation :  @DirtiesContext 
 Use : Method
 Description : Indicates that a method dirties the Spring container and thus it must
 be rebuilt after the test completes.

 #### 5. Annotation :  @ExpectedException
 Use : Method
 Description :  Indicates that the test method is expected to throw a specific exception. The test will fail if the exception is not thrown.
 
 #### 6. Annotation :  @IfProfileValue 
 Use : Type, Method
 Description : Indicates that the test class or method is enabled for a specific profile configuration.

 #### 7. Annotation :  @NotTransactional
 Use:  Method
 Description : Indicates that a test method must not execute in a transactional context.

 #### 8. Annotation :  @ProfileValueSourceConfiguration
 Use : Type
 Description : Identifies an implementation of a profile value source. The absence
 of this annotation will cause profile values to be loaded from system properties.

 #### 9. Annotation :  @Repeat
 Use : Method
 Description : Indicates that the test method must be repeated a specific number of times.

 #### 10. Annotation :  @Rollback
 Use : Method
 Description : Specifies whether or not the transaction for the annotated method should be rolled back or not.

 #### 11. Annotation :  @TestExecutionListeners
 Use : Type
 Description : Identifies zero or more test execution listeners for a test class.

 #### 12. Annotation :  @Timed
 Use : Method
 Description : Specifies a time limit for the test method. If the test does not complete before the time has expired, the test will fail.

 #### 13. Annotation :  @TransactionConfiguration
 Use : Type
 Description :  Configures test classes for transactions, specifying the transaction manager and/or the default rollback rule for all test methods in a test class.  


