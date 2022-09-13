package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.service.RoleService;
import com.xxxx.crm.vo.Role;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    //查询所有角色
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer id){
        return roleService.queryAllRoles(id);
    }

    //打开首页
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

    //查询所有数据
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> userList(RoleQuery roleQuery){
        return roleService.queryByParamsForTable(roleQuery);
    }

    //加载添加/修改页面
    @RequestMapping("addOrUpdateRolePage")
    public String addUserPage(Integer id, HttpServletRequest request){
        if(null !=id){
            request.setAttribute("role",roleService.selectByPrimaryKey(id));
        }
        return "role/add_update";
    }

    //添加角色
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveRole(Role role){
        roleService.saveRole(role);
        return success("角色记录添加成功");
    }

    //添加修改
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("角色记录更新成功");
    }

    //删除角色
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer id,@RequestParam(value = "abc") List<Integer> abc){
        for(Integer a:abc){
            System.out.println(a);
        }
        roleService.deleteRole(id);
        return success("角色记录删除成功");
    }


    /*
     * 跳转到授权页面*/

    @RequestMapping("toGrantPage")
    public String toGrantPage(Integer roleId,HttpServletRequest request){
        request.setAttribute("roleId",roleId);
        return "role/grant";
    }


    /**
     * 给角色授权
     */
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId,Integer[] mId){
        roleService.addGrant(roleId,mId);
        return success("角色授权成功");
    }
}
