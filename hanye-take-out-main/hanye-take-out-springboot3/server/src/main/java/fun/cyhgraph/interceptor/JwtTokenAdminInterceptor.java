package fun.cyhgraph.interceptor;

import fun.cyhgraph.constant.JwtClaimsConstant;
import fun.cyhgraph.context.BaseContext;
import fun.cyhgraph.properties.JwtProperties;
import fun.cyhgraph.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // ========== 白名单配置 ==========
        String[] whiteList = {
                "/admin/employee/login",
                "/admin/employee/logout",
                "/admin/ai/chat",      // AI客服接口放行
                "/admin/ai/stream"     // 流式接口放行
        };

        String requestURI = request.getRequestURI();
        for (String whiteUrl : whiteList) {
            if (requestURI.contains(whiteUrl)) {
                log.info("白名单路径放行: {}", requestURI);
                return true;
            }
        }
        // ========== 白名单配置结束 ==========


        String token = request.getHeader(jwtProperties.getEmployeeTokenName());
        System.out.println("-------------------------------- token -------------------------------- " + token);

        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getEmployeeSecretKey(), token);
            Integer EmployeeId = Integer.valueOf(claims.get(JwtClaimsConstant.EMPLOYEE_ID).toString());
            log.info("当前用户id：{}", EmployeeId);
            BaseContext.setCurrentId(EmployeeId);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
    }
}