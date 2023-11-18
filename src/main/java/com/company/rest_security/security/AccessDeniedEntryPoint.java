package com.company.rest_security.security;

import com.company.rest_security.exception.ErrorResponse;
import com.company.rest_security.logging.LoggingAspect;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AccessDeniedEntryPoint implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        LOGGER.error("Access to resource denied", accessDeniedException);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.toString(),
                                                        "You do not have permission to access this resource",
                                                        LocalDateTime.now());
        response.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(errorResponse)));

    }
}
