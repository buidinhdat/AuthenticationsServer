package com.mobifone.crm.authentication.Configuration;

import com.mobifone.crm.authentication.Configuration.Service.JwtTokenService;
import com.mobifone.crm.authentication.Entity.UserEntity;
import com.mobifone.crm.authentication.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JWTFilterConfig extends HttpFilter {
    public Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    public JwtTokenService jwtTokenService;

    @Autowired
    public TokenJwtUtil tokenJwtUtil;

    @Autowired
    public UserService userService;


    @Override
    protected void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenService.getToken(servletRequest);
        logger.info(token);
        if (token != null && tokenJwtUtil.isValidToken(token, true)) {
            //get UserId
            Long userId = tokenJwtUtil.getUserIdFromJwtToken(token);
            UserEntity userDetails = userService.getUserByUserId(userId); //get USER DETAIL
            if (userDetails != null) {
                try {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.EMPTY_LIST);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception ex) {
                    String mess = ex.toString();
                    if (mess.matches("(?i)(.*)jwt(.*)")) {//Bắn ra HTTP status 401 khi xác thực JWT gặp lỗi
                        servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    } else {
                        throw ex;
                    }
                }

            }
        }
        filterChain.doFilter(servletRequest, servletResponse);



    }
}
