package com.danney.auth.sec;

import com.danney.auth.db.User;
import com.danney.auth.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final String AUTH_HEADER_KEY = "Authorization";
    private final String AUTH_HEADER_PFX = "Bearer ";

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = req.getHeader(AUTH_HEADER_KEY);

        if(null != SecurityContextHolder.getContext().getAuthentication() || null == authHeader
                || !authHeader.startsWith(AUTH_HEADER_PFX)) {
            filterChain.doFilter(req, resp);
            return;
        }

        String jwt = authHeader.substring(AUTH_HEADER_PFX.length());

        try {
            String username = jwtUtil.userFrom(jwt);
            User user = usersService.findByUsername(username);
            if(jwtUtil.validate(jwt, user)) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }catch(UsernameNotFoundException e) {
            System.out.println("User could not be found for authentication");
        }

        filterChain.doFilter(req, resp);
    }
}
