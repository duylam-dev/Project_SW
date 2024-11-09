package it3180.team19.walletapi.Util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


import java.io.PrintWriter;
import java.util.List;

public class AuthInterceptor implements HandlerInterceptor {
    //after this class will use to authorize
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String path = request.getServletPath();
//        List<String> permisstions = SecurityUtil.getPermisstion();
//        if (!permisstions.contains("ROLE_" + path)) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            PrintWriter writer = response.getWriter();
//            writer.write("{\"status\":\"403\",\"message\":\"Forbidden\"}");
//            return false;
//        }
        return true;
    }
}
