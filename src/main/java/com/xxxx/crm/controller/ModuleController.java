package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.service.ModuleService;
import com.xxxx.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    public static void main(String[] args) {
        System.out.println();
    }

    @Resource
    private ModuleService moduleService;
    

    //查询所有的模块资源
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeModel> queryAllModule(Integer roleId){
        return moduleService.queryAllModule(roleId);
    }


    @RequestMapping("index")
    public String toIndex(){
        return "module/module";
    }

    @RequestMapping("toAdd")
    public String toAdd(Integer grade, Integer parentId, HttpServletRequest request){
        request.setAttribute("grade",grade);
        request.setAttribute("parentId",parentId);

        return "module/add";
    }

    @RequestMapping("updateModulePage")
    public String updateModulePage(Integer id,HttpServletRequest request){
        Module module = moduleService.selectByPrimaryKey(id);
        request.setAttribute("module",module);
        return "module/update";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModuleList(){
        return moduleService.queryAllModules();
    }


    @RequestMapping("add")
    @ResponseBody
    public ResultInfo moduleAdd(Module module){
        moduleService.moduleAdd(module);
        return success("资源添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo moduleUpdate(Module module){
        moduleService.moduleUpdate(module);
        return success("资源修改成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo moduledelete(Integer mId){
        moduleService.moduledelete(mId);
        return success("资源删除成功");
    }
}
