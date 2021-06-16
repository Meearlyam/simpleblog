package com.innowisegroup.simpleblog.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class SimpleBlogWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);

        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic appServlet = servletContext.addServlet("simple-blog-rest", servlet);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/*");
    }
}
