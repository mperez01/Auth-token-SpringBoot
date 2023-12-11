package com.mperez.security;

import com.mperez.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER = "Bearer ";

  private final JwtService jwtService;

  private final UserDetailsService userDetailService;

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith(BEARER)) {
      final String jwt = authHeader.substring(BEARER.length());
      final String userEmail = this.jwtService.extractUsername(jwt);
      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        final UserDetails userDetails = this.userDetailService.loadUserByUsername(userEmail);
        if (this.jwtService.isTokenValid(jwt, userDetails)) {
          final UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
