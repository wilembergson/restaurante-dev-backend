package com.example.backend.security;

import com.example.backend.exceptions.DefaultError;
import com.example.backend.repository.EmploeeyUsrRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    EmploeeyUsrRepository userEmploeeyRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = this.recuperarToken(request);
            if (token != null) {
                var tokenPayload = tokenService.validateToken(token);
                UserDetails user = userEmploeeyRepository.findByLogin(tokenPayload.login());
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }catch (DefaultError e){
            response.setStatus(e.getStatusCode().value());
            response.setCharacterEncoding("UTF-8");
            String jsonErrorMessage = "{\"mensagem\":\"" + e.getMessage() + "\"}";
            response.getWriter().write(jsonErrorMessage);
        }
    }

    private String recuperarToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
