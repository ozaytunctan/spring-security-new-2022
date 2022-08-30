package com.ozaytunctan.security.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozaytunctan.dtos.Error;
import com.ozaytunctan.dtos.ServiceResultDto;
import com.ozaytunctan.security.manager.JwtTokenManager;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager tokenManager;

    private final ObjectMapper objectMapper;

    private final Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(JwtTokenManager tokenManager, ObjectMapper objectMapper) {
        this.tokenManager = tokenManager;
        this.objectMapper = objectMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String token = this.tokenManager.getToken(request);

            //token yok ise spring security bırakılıyor.
            if (!StringUtils.hasText(token)) {
                chain.doFilter(request, response);
                return;
            }

            //token var ve valid ise
            if (tokenManager.isValid(token)) {

                String principal = tokenManager.getUsernameFromToken(token);

                List<SimpleGrantedAuthority> roles = tokenManager.getRolesFromToken(token);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        principal, null, roles);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                //set cookie
               // response.addCookie(new Cookie(Constants.SESSION_ID, token));
                chain.doFilter(request, response);
            } else {

                if (isBasicAuthRequest(request)) {
                    SecurityContextHolder.clearContext();
                    chain.doFilter(request, response);
                    return;
                }

                prepareInvalidAuthResponse(response);
                return;
            }

        } catch (ExpiredJwtException | BadCredentialsException ex) {
            prepareInvalidAuthResponse(response);
            logger.info("JwtAuthenticationFilter filter error:",ex);
        } catch (Exception ex) {
            logger.info("JwtAuthenticationFilter filter error:",ex);
            prepareInvalidAuthResponse(response);
        }
    }

    private boolean isBasicAuthRequest(HttpServletRequest request) {
        String data = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (StringUtils.hasText(data) && data.startsWith("Basic "));
    }

    private void prepareInvalidAuthResponse(HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("INVALID-AUTH", "Invalid authentication attempt!");
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON.toString());
        try {
            response.getWriter().write(convertObjectToJson(new Error("Invalid authentication attempt!",401)));
        } catch (Exception e) {
        }

    }

    private String convertObjectToJson(Error error ){
        try {
            return this.objectMapper.writeValueAsString(new ServiceResultDto<>(Arrays.asList(error)));
        } catch (JsonProcessingException e) {
            return "";
        }
    }


}