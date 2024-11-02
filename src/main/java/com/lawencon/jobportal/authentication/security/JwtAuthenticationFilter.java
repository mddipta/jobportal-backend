package com.lawencon.jobportal.authentication.security;

import com.lawencon.jobportal.authentication.service.JwtService;
import com.lawencon.jobportal.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    private static final List<String> URL_NOT_FILTER =
            List.of("/api/v1/login", "/api/v1/register", "api/v1/user/verifcation");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return URL_NOT_FILTER.contains(request.getRequestURI());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid or expired JWT token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
