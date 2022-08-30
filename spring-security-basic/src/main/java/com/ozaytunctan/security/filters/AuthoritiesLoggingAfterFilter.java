package com.ozaytunctan.security.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;
import java.util.Objects;

public class AuthoritiesLoggingAfterFilter implements Filter {


    private final  Logger LOG = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter.class);

    public AuthoritiesLoggingAfterFilter() {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)) {
            LOG.info("User " + authentication.getName() + " is successfully authenticated and has the authorities" + authentication.getAuthorities());
        }

        filterChain.doFilter(request, response);
    }
}
