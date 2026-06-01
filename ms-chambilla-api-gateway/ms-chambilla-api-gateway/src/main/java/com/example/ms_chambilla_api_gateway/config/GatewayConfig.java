package com.example.ms_chambilla_api_gateway.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;

@Configuration
public class GatewayConfig {

    private static final Logger log = LoggerFactory.getLogger(GatewayConfig.class);

    /**
     * Filtro de Logging Global para el Gateway MVC.
     * Como usamos spring-cloud-starter-gateway-server-webmvc, un filtro Servlet
     * actúa como filtro global registrando todas las peticiones entrantes.
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public Filter globalLoggingFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse res = (HttpServletResponse) response;
                
                long startTime = System.currentTimeMillis();
                log.info("Incoming request: Method={}, URI={}", req.getMethod(), req.getRequestURI());

                try {
                    chain.doFilter(request, response);
                } finally {
                    long duration = System.currentTimeMillis() - startTime;
                    log.info("Outgoing response: Status={}, Duration={}ms", res.getStatus(), duration);
                }
            }
        };
    }

    /**
     * Filtro CORS Global
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Permitir todos los orígenes en desarrollo
        config.addAllowedOriginPattern("*");
        // Permitir los métodos HTTP especificados
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        // Permitir cabeceras necesarias
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
