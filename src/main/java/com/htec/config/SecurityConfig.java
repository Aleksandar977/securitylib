package com.htec.config;

import com.htec.security.filter.JwtAuthenticationFilter;
import com.htec.security.entrypoints.UserAuthenticationEntryPoint;
import com.htec.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtService jwtService;

    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests(c -> {
                    c.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtService), BasicAuthenticationFilter.class)
                .exceptionHandling(c -> {
                    c.authenticationEntryPoint(new UserAuthenticationEntryPoint());
                })
                .csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
