package com.baoviet.mhol.web.config;

import javax.servlet.*;

import com.google.common.collect.Sets;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by levietcongitsol on 7/10/2017.
 */
public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        sc.setSessionTrackingModes(Sets.immutableEnumSet(SessionTrackingMode.COOKIE));
        SessionCookieConfig cookieConfig = sc.getSessionCookieConfig();
        cookieConfig.setHttpOnly(true);

        // Spring Root Context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class);
        sc.addListener(new ContextLoaderListener(rootContext));


        // Spring Servlet Context
        AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletConfig.class);
        ServletRegistration.Dynamic dispatcher = sc.addServlet("pmpServlet", new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        sc.addFilter("Encoding Filter", characterEncodingFilter);
    }

}