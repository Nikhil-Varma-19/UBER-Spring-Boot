package com.example.uber.uber_backend.filters;

import com.example.uber.uber_backend.entities.User;
import com.example.uber.uber_backend.services.CustomerUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTFilter jwtFilter;
    private final CustomerUserService customerUserService;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            final String requestToken=request.getHeader("Authorization");
            if(requestToken == null || !requestToken.startsWith("Bearer")){
                filterChain.doFilter(request,response);
                return;
            }
            String getTokenFromHeader=requestToken.split(" ")[1];
            Long userId=jwtFilter.getUserIdFromToken(getTokenFromHeader);
            if(userId != null  && SecurityContextHolder.getContext().getAuthentication() == null){
                User user=customerUserService.getUserById(userId);
                UsernamePasswordAuthenticationToken authToken=
                        new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }
    }

}
