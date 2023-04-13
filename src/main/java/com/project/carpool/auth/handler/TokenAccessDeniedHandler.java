package com.project.carpool.auth.handler;

<<<<<<< HEAD
=======
import jakarta.servlet.ServletException;
>>>>>>> 6b4c42c2946f3c21dfeafce58b142dddd0e2be07
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAccessDeniedHandler implements AccessDeniedHandler {
    @Override
<<<<<<< HEAD
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws  IOException {
=======
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException{
>>>>>>> 6b4c42c2946f3c21dfeafce58b142dddd0e2be07
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
