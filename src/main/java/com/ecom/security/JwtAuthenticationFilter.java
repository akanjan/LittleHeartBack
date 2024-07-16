package com.ecom.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        // 1. Get Token
        String requestToken = request.getHeader("Authorization");
        System.out.println(requestToken);

        String username=null;
        String token=null;

        if (requestToken !=null && requestToken.startsWith("Bearer "))
        {
            //Yes
            token=requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Unable to get Jwt Token");
            }
            catch (ExpiredJwtException e)
            {
                System.out.println("jwt token has expired");
            }
            catch (MalformedJwtException e)
            {
                System.out.println("Invalid Jwt");
            }


        }else
        {
            System.out.println("Invalid Token, Not Start With Bearer");
        }

        //Validate
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token,userDetails))
            {
                //Token is Valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
            }
        }else
        {
            System.out.println("Token Is Not Valid");
        }
        filterChain.doFilter(request,response);

    }

}
