package com.innowisegroup.simpleblog.config;

import com.innowisegroup.simpleblog.model.User;
import com.innowisegroup.simpleblog.repository.GenericRepository;
import com.innowisegroup.simpleblog.repository.GenericRepositoryImpl;
import com.innowisegroup.simpleblog.service.UserService;
import com.innowisegroup.simpleblog.service.UserServiceImpl;
import com.innowisegroup.simpleblog.service.mapping.UserMappingService;
import com.innowisegroup.simpleblog.service.mapping.UserMappingServiceImpl;
import com.innowisegroup.simpleblog.service.validation.UserValidationService;
import com.innowisegroup.simpleblog.service.validation.UserValidationServiceImpl;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
@ComponentScan({
        "com.innowisegroup.simpleblog.repository",
        "com.innowisegroup.simpleblog.service",
        "com.innowisegroup.simpleblog.controller"
})
public class WebConfig implements WebMvcConfigurer {

    private Environment env;

    @Autowired
    public WebConfig(Environment env) {
        this.env = env;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.innowisegroup.simpleblog.model");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty(
                "hibernate.dialect", env.getProperty("hibernate.dialect"));

        return hibernateProperties;
    }

    @Bean
    public UserMappingService userMappingService() {
        return new UserMappingServiceImpl();
    }

    @Bean
    public GenericRepository<User> userRepository() {
        return new GenericRepositoryImpl<>(sessionFactory().getObject(), User.class);
    }

    @Bean
    public UserValidationService userValidationService() {
        return new UserValidationServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository(), userMappingService(), userValidationService());
    }
}
