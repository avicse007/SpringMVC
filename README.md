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


## Spring Type Conversions

### 1. Introduction

Spring provides out-of-the-box various converters for built-in types; this means converting to/from basic types like String, Integer, Boolean and a number of other types.
Apart from this, Spring also provides a solid type conversion SPI for developing our custom converters.

### 2. Built-in Converters

We'll start with the converters available out-of-the-box in Spring; let's have a look at the String to Integer conversion:
	
@Autowired
ConversionService conversionService;
 
@Test
public void whenConvertStringToIntegerUsingDefaultConverter_thenSuccess() {
    assertThat(
      conversionService.convert("25", Integer.class)).isEqualTo(25);
}

The only thing we need to do here is to autowire the ConversionService provided by Spring and call the convert() method. The first argument is the value that we want to convert and the second argument is the target type that we want to convert to.

### 3. Creating a Custom Converter

Let's have a look at an example of converting a String representation of an Employee to an Employee instance.

Here's the Employee class:

public class Employee {
 
    private long id;
    private double salary;
 
    // standard constructors, getters, setters
}

The String will be a comma-separated pair representing id and salary. For example, “1,50000.00”.

In order to create our custom Converter, we need to implement the Converter<S, T> interface and implement the convert() method:

public class StringToEmployeeConverter
  implements Converter<String, Employee> {
 
    @Override
    public Employee convert(String from) {
        String[] data = from.split(",");
        return new Employee(
          Long.parseLong(data[0]), 
          Double.parseDouble(data[1]));
    }
}

We're not done yet. We also need to tell Spring about this new converter by adding the StringToEmployeeConverter to the FormatterRegistry. This can be done by implementing the WebMvcConfigurer and overriding addFormatters() method:
	
@Configuration
public class WebConfig implements WebMvcConfigurer {
 
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEmployeeConverter());
    }
}

And that's it. Our new Converter is now available to the ConversionService and we can use it in the same way as any other built-in Converter:
	
@Test
public void whenConvertStringToEmployee_thenSuccess() {
    Employee employee = conversionService
      .convert("1,50000.00", Employee.class);
    Employee actualEmployee = new Employee(1, 50000.00);
     
    assertThat(conversionService.convert("1,50000.00", 
      Employee.class))
      .isEqualToComparingFieldByField(actualEmployee);
}

### 3.1. Implicit Conversion

Beyond these explicit conversion using the ConversionService, Spring is also capable of implicitly converting values right in Controller methods for all registered converters:
	
@RestController
public class StringToEmployeeConverterController {
 
    @GetMapping("/string-to-employee")
    public ResponseEntity<Object> getStringToEmployee(
      @RequestParam("employee") Employee employee) {
        return ResponseEntity.ok(employee);
    }
}

This is a more natural way of using the Converters. Let's add a test to see it in action:
	
@Test
public void getStringToEmployeeTest() throws Exception {
    mockMvc.perform(get("/string-to-employee?employee=1,2000"))
      .andDo(print())
      .andExpect(jsonPath("$.id", is(1)))
      .andExpect(jsonPath("$.salary", is(2000.0)))
}

As you can see, the test will print all the details of the request as well as the response. Here is the Employee object in JSON format that is returned as part of the response:
{"id":1,"salary":2000.0}

### 4. Creating a ConverterFactory

It's also possible to create a ConverterFactory that creates Converters on demand. This is particularly helpful in creating Converters for Enums.

Let's have a look at a really simple Enum:

public enum Modes {
    ALPHA, BETA;
}

Next, let's create a StringToEnumConverterFactory that can generate Converters for converting a String to any Enum:

@Component
public class StringToEnumConverterFactory 
  implements ConverterFactory<String, Enum> {
 
    private static class StringToEnumConverter<T extends Enum> 
      implements Converter<String, T> {
 
        private Class<T> enumType;
 
        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }
 
        public T convert(String source) {
            return (T) Enum.valueOf(this.enumType, source.trim());
        }
    }
 
    @Override
    public <T extends Enum> Converter<String, T> getConverter(
      Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }
}

As we can see, the factory class internally uses an implementation of Converter interface.

One thing to note here is that although we'll use our Modes Enum to demonstrate the usage, we haven't mentioned the Enum anywhere in the StringToEnumConverterFactory. Our factory class is generic enough to generate the Converters on demand for any Enum type.

The next step is to register this factory class as we registered our Converter in the previous example:
	
@Override
public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new StringToEmployeeConverter());
    registry.addConverterFactory(new StringToEnumConverterFactory());
}

Now the ConversionService is ready to convert Strings to Enums:
	
@Test
public void whenConvertStringToEnum_thenSuccess() {
    assertThat(conversionService.convert("ALPHA", Modes.class))
      .isEqualTo(Modes.ALPHA);
}

### 5. Creating a GenericConverter

A GenericConverter provides us more flexibility to create a Converter for a more generic use at the cost of losing some type safety.

Let's consider an example of converting an Integer, Double, or a String to a BigDecimal value.We don't need to write three Converters for this. A simple GenericConverter could serve the purpose.

The first step is to tell Spring what types of conversion are supported. We do this by creating a Set of ConvertiblePair:
	
public class GenericBigDecimalConverter 
  implements GenericConverter {
 
@Override
public Set<ConvertiblePair> getConvertibleTypes () {
 
    ConvertiblePair[] pairs = new ConvertiblePair[] {
          new ConvertiblePair(Number.class, BigDecimal.class),
          new ConvertiblePair(String.class, BigDecimal.class)};
        return ImmutableSet.copyOf(pairs);
    }
}

The next step is to override the convert() method in the same class:
	
@Override
public Object convert (Object source, TypeDescriptor sourceType, 
  TypeDescriptor targetType) {
 
    if (sourceType.getType() == BigDecimal.class) {
        return source;
    }
 
    if(sourceType.getType() == String.class) {
        String number = (String) source;
        return new BigDecimal(number);
    } else {
        Number number = (Number) source;
        BigDecimal converted = new BigDecimal(number.doubleValue());
        return converted.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}

The convert() method is as simple as it can be. However, the TypeDescriptor provides us great flexibility in terms of getting the details concerning the source and the target type.

As you might have already guessed, the next step is to register this Converter:
	
@Override
public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new StringToEmployeeConverter());
    registry.addConverterFactory(new StringToEnumConverterFactory());
    registry.addConverter(new GenericBigDecimalConverter());
}

Using this Converter is similar to the other examples that we've already seen:
	
@Test
public void whenConvertingToBigDecimalUsingGenericConverter_thenSuccess() {
    assertThat(conversionService
      .convert(Integer.valueOf(11), BigDecimal.class))
      .isEqualTo(BigDecimal.valueOf(11.00)
      .setScale(2, BigDecimal.ROUND_HALF_EVEN));
    assertThat(conversionService
      .convert(Double.valueOf(25.23), BigDecimal.class))
      .isEqualByComparingTo(BigDecimal.valueOf(Double.valueOf(25.23)));
    assertThat(conversionService.convert("2.32", BigDecimal.class))
      .isEqualTo(BigDecimal.valueOf(2.32));
}












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


### JSR-380 ANNOTATIONS


   #### 1. @NotNull 
    – validates that the annotated property value is not null
   #### 2.  @AssertTrue 
    – validates that the annotated property value is true
   #### 3. @Size 
    – validates that the annotated property value has a size between the attributes min and max; can be applied to String, Collection,   
     Map, and array properties
   #### 4. @Min  
    -Validates that the annotated property has a value no smaller than the value attribute
   #### 5. @Max 
    – validates that the annotated property has a value no larger than the value attribute
   #### 6. @Email 
    – validates that the annotated property is a valid email address
   #### 7. @NotEmpty 
    – validates that the property is not null or empty; can be applied to String, Collection, Map or Array values
   #### 8. @NotBlank 
    – can be applied only to text values and validated that the property is not null or whitespace
   #### 9. @Positive and @PositiveOrZero 
    – apply to numeric values and validate that they are strictly positive, or positive including 0
   #### 10. @Negative and @NegativeOrZero 
    – apply to numeric values and validate that they are strictly negative, or negative including 0
   #### 11. @Past and @PastOrPresent 
    – validate that a date value is in the past or the past including the present; can be applied to date types including those added in 
      Java 8
   #### 12. @Future and @FutureOrPresent 
    – validates that a date value is in the future, or in the future including the present
    
   #### 13. @Valid
    - have to add @Valid annotation to the Controller method that does the form binding with the user defined form bean and have to  
      provide the binding result to check all the constraints applied on the form bean is validated and has no error. 
     
  #### 14. Using @Enumerated Annotation
  
  The most common option to map an enum value to and from its database representation in JPA before 2.1. is to use the @Enumerated  
  annotation. This way, we can instruct a JPA provider to convert an enum to its ordinal or String value.
  
  But first, let's create a simple @Entity that we'll be using throughout this tutorial:
	
@Entity
public class Article {
    @Id
    private int id;
 
    private String title;
 
    // standard constructors, getters and setters
}

##### 2.1. Mapping Ordinal Value

If we put the @Enumerated(EnumType.ORDINAL) annotation on the enum field, JPA will use the Enum.ordinal() value when persisting a given entity in the database.

Let's introduce the first enum:
public enum Status {
    OPEN, REVIEW, APPROVED, REJECTED;
}

Next, let's add it to the Article class and annotate it with @Enumerated(EnumType.ORDINAL):
@Entity
public class Article {
    @Id
    private int id;
 
    private String title;
 
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}

Now, when persisting an Article entity:

Article article = new Article();
article.setId(1);
article.setTitle("ordinal title");
article.setStatus(Status.OPEN);

JPA will trigger the following SQL statement:
	
insert
into
    Article
    (status, title, id) 
values
    (?, ?, ?)
binding parameter [1] as [INTEGER] - [0]
binding parameter [2] as [VARCHAR] - [ordinal title]
binding parameter [3] as [INTEGER] - [1]

A problem with this kind of mapping arises when we need to modify our enum. If we add a new value in the middle or rearrange the enum's order, we'll break the existing data model.

Such issues might be hard to catch, as well as problematic to fix, as we would have to update all the database records.
##### 2.2. Mapping String Value

Analogously, JPA will use the Enum.name() value when storing an entity if we annotate the enum field with @Enumerated(EnumType.STRING).

Let's create the second enum:	
public enum Type {
    INTERNAL, EXTERNAL;
}

And let's add it to our Article class and annotate it with @Enumerated(EnumType.STRING):
	
@Entity
public class Article {
    @Id
    private int id;
 
    private String title;
 
    @Enumerated(EnumType.ORDINAL)
    private Status status;
 
    @Enumerated(EnumType.STRING)
    private Type type;
}

Now, when persisting an Article entity:
	
Article article = new Article();
article.setId(2);
article.setTitle("string title");
article.setType(Type.EXTERNAL);

JPA will execute the following SQL statement:
	
insert
into
    Article
    (status, title, type, id) 
values
    (?, ?, ?, ?)
binding parameter [1] as [INTEGER] - [null]
binding parameter [2] as [VARCHAR] - [string title]
binding parameter [3] as [VARCHAR] - [EXTERNAL]
binding parameter [4] as [INTEGER] - [2]

With @Enumerated(EnumType.STRING), we can safely add new enum values or change our enum's order. However, renaming an enum value will still break the database data.

Additionally, even though this data representation is far more readable compared to the @Enumerated(EnumType.ORDINAL) option, it also consumes a lot more space than necessary. This might turn out to be a significant issue when we need to deal with a high volume of data.
##### 3. Using @PostLoad and @PrePersist annotations

Another option we have to deal with persisting enums in a database is to use standard JPA callback methods. We can map our enums back and forth in the @PostLoad and @PrePersist events.

The idea is to have two attributes in an entity. The first one is mapped to a database value, and the second one is a @Transient field that holds a real enum value. The transient attribute is then used by the business logic code.

To better understand the concept, let's create a new enum and use its int value in the mapping logic:
	
public enum Priority {
    LOW(100), MEDIUM(200), HIGH(300);
 
    private int priority;
 
    private Priority(int priority) {
        this.priority = priority;
    }
 
    public int getPriority() {
        return priority;
    }
 
    public static Priority of(int priority) {
        return Stream.of(Priority.values())
          .filter(p -> p.getPriority() == priority)
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}

We've also added the Priority.of() method to make it easy to get a Priority instance based on its int value.


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


