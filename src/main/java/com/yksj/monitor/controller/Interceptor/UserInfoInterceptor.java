package com.yksj.monitor.controller.Interceptor;

import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserInfoInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求，访问controller 之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String userId = request.getHeader("UserId");
        String userToken = request.getHeader("UserToken");
        if (StringUtils.isEmptyOrWhitespaceOnly(userId) || StringUtils.isEmptyOrWhitespaceOnly(userToken)) {
            log.error("用户信息不能为空");
            return false;
        }

        //  userId:1001 userToken:abc123
        if (!userId.equalsIgnoreCase("1001")||!userToken.equalsIgnoreCase("abc123")){
            log.error("用户没有权限");
            return false;
        }

        //return true;
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 请求访问到controller 之后，渲染视图之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 请求访问到controller 之后，渲染视图之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
