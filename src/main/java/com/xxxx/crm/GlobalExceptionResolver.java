package com.xxxx.crm;

import com.alibaba.fastjson.JSON;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.NoLoginException;
import com.xxxx.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    /**
     * 控制层的方法返回的内容两种情况
     * 	    1. 视图:视图异常
     *   	2. Json:方法执行错误 返回错误json信息
     * @param request
     * @param response
     * @param handler   
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();

        if(ex instanceof NoLoginException){
            NoLoginException ne = (NoLoginException)ex;
            // mv.setViewName("index");   目前是直接去找视图
            //目的是跳转到登录页面   必须通过接口才能显示
            mv.setViewName("redirect:index");
            return mv;
        }

        //设置默认的异常处理
        mv.setViewName("error");
        mv.addObject("code",300);
        mv.addObject("msg","数据异常，请重试");
       
        //判断目标方法返回的是视图还是json数据
        if(handler instanceof HandlerMethod){
            //转换成controller方法对象
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            //获取responsebody注解对象
            ResponseBody reponsebody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);

            //判断当前方法是否存在responsebody注解
            if(reponsebody == null){
                //返回视图的接口异常处理

                if(ex instanceof ParamsException){
                    ParamsException pe = (ParamsException)ex;
                    mv.addObject("code",pe.getCode());
                    mv.addObject("msg",pe.getMsg());
                }
                return mv;
            }else{
                //返回json的接口异常处理
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("系统异常请重试");

                //判断是否是自定义异常
                if(ex instanceof ParamsException){
                    ParamsException pe = (ParamsException)ex;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }

                //将resultinfo数据传给前台的ajax回调函数

                //设置数据传输的类型和编码格式
                response.setContentType("application/json;charset=utf-8");

                PrintWriter writer = null;
                try {
                    //获取输出流
                    writer = response.getWriter();
                    //将数据对象转换成json格式的，传输出去
                    writer.write(JSON.toJSONString(resultInfo));
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(writer != null){
                        writer.close();
                    }
                }
                return null;
            }
        }

        return mv;
    }
}
