package com.xxxx.crm.interceptors;

import com.xxxx.crm.exceptions.NoLoginException;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    /**
     * 在请求到达目标接口之前，拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        //通过 cookie中的userIdStr 判断用户是否是登录状态
//        int id = LoginUserUtil.releaseUserIdFromCookie(request);
//        if(id == 0 || null == userService.selectByPrimaryKey(id)){
//            throw new NoLoginException();
//        }

        return true; //放行 执行目标接口方法
    }
}
