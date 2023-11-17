package com.group4.logindemo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if ("POST".equalsIgnoreCase(request.getMethod()) && "/login".equals(request.getServletPath())) {
            HttpSession session = request.getSession();
            String requestCaptcha = request.getParameter("captcha");
            String sessionCaptcha = (String) session.getAttribute("captcha");

            if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(requestCaptcha)) {
                // Captcha not entered correctly
                AuthenticationException failed = new AuthenticationException("Captcha entered incorrectly") {};
                new SimpleUrlAuthenticationFailureHandler().onAuthenticationFailure(request, response, failed);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
