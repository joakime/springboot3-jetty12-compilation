package com.example.demo;

import jakarta.servlet.ServletRegistration;
import org.eclipse.jetty.ee10.servlet.DefaultServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import java.net.URL;

@SpringBootApplication
public class DemoApplication implements WebServerFactoryCustomizer<JettyServletWebServerFactory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void customize(JettyServletWebServerFactory factory) {

        DefaultServlet defaultServlet = new DefaultServlet();
        factory.addInitializers(ctx -> {

            ServletRegistration sr = ctx.addServlet("swagger-ui", defaultServlet);
            sr.addMapping("/swagger-ui/*");

            URL externalForm = DemoApplication.class.getResource("/static/swagger-ui/");

            LOGGER.info("### resources: " + externalForm.toExternalForm());
            sr.setInitParameter("baseResource", externalForm.toExternalForm());
            sr.setInitParameter("pathInfoOnly", "true");

        });
        LOGGER.info("WebServer customized with swagger-ui");

    }
}
