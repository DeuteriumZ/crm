package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.service.PermissionService;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;

    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("user/add_Rgister")
    public String add_Rgister(){
        return "user/add_Rgister";
    }


    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 后端管理主页面
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
//        //获取id
//        int id = LoginUserUtil.releaseUserIdFromCookie(request);
//        //tonggid查询
//        User user = userService.selectByPrimaryKey(id);
//        request.setAttribute("user",user);
//
//        //当用户登录后进去主页面之前将当前用户具备所有的权限码查询出来，放在session作用域中
//        List<Integer> permission =  permissionService.selectAclvalueByUserId(id);
//        request.getSession().setAttribute("permission",permission);//权限码
//        System.out.println(permission.toString());
        return "main";
    }
}
