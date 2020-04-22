package com.satyy.resourceserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * To provide custom response on invalid token (401)
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {

        LOGGER.info("Invalid token in the request: {},  Error: {}", request.getRequestURI(), authException.getMessage());

        final Map<String, java.io.Serializable> map = new LinkedHashMap<>();
        map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        map.put("message", authException.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            LOGGER.error("Error Creating token authentication failure response, {}", e.getMessage());
            throw new ServletException();
        }
    }
}