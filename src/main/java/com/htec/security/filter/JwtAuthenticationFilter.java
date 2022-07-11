package com.htec.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.htec.domain.enums.TokenType;
import com.htec.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.htec.utils.Constants.BEARER;
import static com.htec.utils.Constants.EXCEPTION_LOGGING;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {

            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authorizationHeader.substring(BEARER.length());
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(jwt));
        filterChain.doFilter(request, response);

    }


    private Authentication getAuthentication(String token) {
        try {

            final DecodedJWT decodedToken = jwtService.getDecodedJwt(token);
            final Long userId = jwtService.getUserIdFromToken(decodedToken);
            final TokenType jwtType = jwtService.getTokenTypeFromToken(decodedToken);

            if (!jwtType.equals(TokenType.ACCESS)) {
                return null;
            }

            return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());

        } catch (Exception e) {
            log.warn(EXCEPTION_LOGGING, "User is not authorized", e.getMessage());
            return null;
        }
    }
}
