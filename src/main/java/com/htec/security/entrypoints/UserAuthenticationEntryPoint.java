package com.htec.security.entrypoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htec.domain.enums.ErrorCodeMessage;
import com.htec.domain.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodeMessage.NOT_AUTHORIZED.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
