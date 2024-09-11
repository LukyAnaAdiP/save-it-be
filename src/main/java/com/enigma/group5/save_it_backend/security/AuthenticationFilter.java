package com.enigma.group5.save_it_backend.security;

import com.enigma.group5.save_it_backend.dto.response.JwtClaims;
import com.enigma.group5.save_it_backend.entity.UserAccount;
import com.enigma.group5.save_it_backend.service.JwtService;
import com.enigma.group5.save_it_backend.service.UserAccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    final String AUTH_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UserAccountService userAccountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            SecurityContext context = SecurityContextHolder.createEmptyContext();

            String bearerToken = request.getHeader(AUTH_HEADER);

            if(bearerToken != null && jwtService.verifyJwtToken(bearerToken)){

                JwtClaims decodeJwt = jwtService.getClaimsByToken(bearerToken);

                UserAccount userAccountBySub = userAccountService.getByUserId(decodeJwt.getUserAccountId());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userAccountBySub.getUsername(),
                        null,
                        userAccountBySub.getAuthorities()
                );

                authentication.setDetails(new WebAuthenticationDetails(request));

                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);
            }
        } catch (Exception e){
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request,response);
    }
}

