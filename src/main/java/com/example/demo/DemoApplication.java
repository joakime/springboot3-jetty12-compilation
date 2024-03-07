package com.example.demo;

import java.io.IOException;
import java.net.URL;

import org.eclipse.jetty.ee10.servlet.DefaultServlet;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.AllowedResourceAliasChecker;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class DemoApplication implements WebServerFactoryCustomizer<JettyServletWebServerFactory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void customize(JettyServletWebServerFactory factory) {
        factory.addInitializers(ctx -> {
            ServletContextHandler servletContextHandler = ServletContextHandler.getServletContextHandler(ctx);
            ServletHolder holder = new ServletHolder("swatter-ui", new DefaultServlet());
            servletContextHandler.addServlet(holder, "/swagger-ui/*");

            try {
                URL externalForm = new ClassPathResource("/static/swagger-ui/").getURL();
                LOGGER.info("### resources: " + externalForm.toExternalForm());
                holder.setInitParameter("baseResource", externalForm.toExternalForm());
                Resource base = servletContextHandler.newResource(externalForm);
                servletContextHandler.addAliasCheck(new AllowedResourceAliasChecker(servletContextHandler, base));
            } catch (IOException e) {
                throw new RuntimeException("Unable to find /static/swagger-ui");
            }
        });
        LOGGER.info("WebServer customized with swagger-ui");
    }
}
