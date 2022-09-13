package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Module;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {

    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;


    //查询所有的模块资源  封装TreeModel
    public List<TreeModel> queryAllModule(Integer roleId){
        //查询所有资源
        List<TreeModel> treeModels = moduleMapper.queryAllModule();
        //查询当前角色拥有权限/资源
        List<Integer> roleMIds = permissionMapper.selectPermissionByRoleId(roleId);
        //比对，如果所有资源中包含了（当前角色拥有权限），设置当前某个资源中的checked属性为true
        if(roleMIds != null && roleMIds.size() > 0){
            for(TreeModel model:treeModels){
                if(roleMIds.contains(model.getId())){
                    model.setChecked(true);//设置选中
                    model.setOpen(true);//设置打开树形
                }
            }
        }

        return treeModels;
    }


    //查询所有的模块资源
    public Map<String,Object> queryAllModules(){
        Map<String,Object> result = new HashMap<String,Object>();
        List<Module> modules = moduleMapper.queryAllModules();
        result.put("count",modules.size());
        result.put("data",modules);
        result.put("code",0);
        result.put("msg","");
        return result;
    }


    /**
     * 模块添加
     *   1.数据校验
     模块名称
     非空，同级唯一
     地址 URL
     二级菜单：非空，同级唯一
     父级菜单 parentId
     一级：null | -1
     二级|三级：非空 | 必须存在
     层级 grade
     非空  值必须为 0|1|2
     权限码
     非空  唯一
     2.默认值
     is_valid
     updateDate
     createDate
     3.执行添加操作  判断受影响行数
     *
     * @param module
     */
    @Transactional
    public void moduleAdd(Module module) {
        //层级 grade  非空  值必须为 0|1|2
        AssertUtil.isTrue(module.getGrade() == null,"层级不能为空");
        AssertUtil.isTrue(!(module.getGrade() == 0 || module.getGrade() == 1 || module.getGrade() == 2),"层级有误");

        //模块名称 非空  同级唯一
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名称不能为空");
        Module dbModule = moduleMapper.queryModulByGradeAName(module.getGrade(),module.getModuleName());
        AssertUtil.isTrue(dbModule != null,"模块名称已存在");

        // 二级菜单URL：非空，同级唯一
        if(module.getGrade() == 1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"模块地址不能为空");
            dbModule = moduleMapper.queryModulByGradeAUrl(module.getGrade(),module.getUrl());
            AssertUtil.isTrue(dbModule != null,"地址已存在，请重新输入");
        }

        //父级菜单  二级|三级：非空 | 必须存在
        if(module.getGrade() == 1 || module.getGrade() == 2){
            AssertUtil.isTrue(module.getParentId() == null,"父ID不能为空");
            dbModule = moduleMapper.queryModulById(module.getParentId());
            AssertUtil.isTrue(dbModule == null,"父ID不存在");
        }

        //权限码  非空  唯一
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空");
        dbModule = moduleMapper.queryModulByOptValue(module.getOptValue());
        AssertUtil.isTrue(dbModule != null,"权限码已存在");

        //默认值
        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());

        //执行添加操作  判断受影响行数
        AssertUtil.isTrue(moduleMapper.insertSelective(module) < 1,"模块添加失败");
    }


    /**
     * 修改模块
     1.数据校验
     id
     非空，并且资源存在
     模块名称
     非空，同级唯一
     地址 URL
     二级菜单：非空，同级唯一
     父级菜单 parentId
     一级：null | -1
     二级|三级：非空 | 必须存在
     层级 grade
     非空  值必须为 0|1|2
     权限码
     非空  唯一
     2.默认值
     is_valid
     updateDate
     createDate
     3.执行修改操作  判断受影响行数
     * @param module
     */
    @Transactional
    public void moduleUpdate(Module module) {
        // id  非空，并且资源存在
        AssertUtil.isTrue(module.getId() == null,"待删除的资源不存在");
        Module dbModule = moduleMapper.selectByPrimaryKey(module.getId());
        AssertUtil.isTrue(dbModule == null,"系统异常");

        //层级 grade  非空  值必须为 0|1|2
        AssertUtil.isTrue(module.getGrade() == null,"层级不能为空");
        AssertUtil.isTrue(!(module.getGrade() == 0 || module.getGrade() == 1 || module.getGrade() == 2),"层级有误");

        //模块名称 非空  同级唯一
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名称不能为空");
        dbModule = moduleMapper.queryModulByGradeAName(module.getGrade(),module.getModuleName());
        AssertUtil.isTrue(dbModule != null && !(module.getId().equals(dbModule.getId())),"模块名称已存在");

        // 二级菜单URL：非空，同级唯一
        if(module.getGrade() == 1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"模块地址不能为空");
            dbModule = moduleMapper.queryModulByGradeAUrl(module.getGrade(),module.getUrl());
            AssertUtil.isTrue(dbModule != null && !(module.getId().equals(dbModule.getId())),"地址已存在，请重新输入");
        }

        //父级菜单  二级|三级：非空 | 必须存在
        if(module.getGrade() == 1 || module.getGrade() == 2){
            AssertUtil.isTrue(module.getParentId() == null,"父ID不能为空");
            dbModule = moduleMapper.queryModulById(module.getParentId());
            AssertUtil.isTrue(dbModule == null && !(module.getId().equals(dbModule.getId())),"父ID不存在");
        }

        //权限码  非空  唯一
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空");
        dbModule = moduleMapper.queryModulByOptValue(module.getOptValue());
        AssertUtil.isTrue(dbModule != null && !(module.getId().equals(dbModule.getId())),"权限码已存在");

        //默认值
        module.setUpdateDate(new Date());

        //执行修改操作
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module) < 1,"资源修改失败");
    }


    /**
     * 逻辑删除资源
     *      逻辑判断
     *          1.参数id 非空，删除的数据必须存在
     *          2.查询当前id下是否有子模块，如果有不能删除
     *          3.查询权限表中(角色和资源)是否包含当前模块的数据，有则删除
     *          4.删除资源
     * @param mId
     */
    @Transactional
    public void moduledelete(Integer mId) {
        //参数id 非空，删除的数据必须存在
        AssertUtil.isTrue(mId == null,"系统异常，请重试");
        AssertUtil.isTrue(selectByPrimaryKey(mId) == null,"待删除数据不存在");

        //查询当前id下是否有子模块，如果有不能删除
        Integer count = moduleMapper.queryCountModuleByParentId(mId);
        AssertUtil.isTrue(count > 0,"该模块下存在子模块，不能删除");

        //查询权限表中(角色和资源)是否包含当前模块的数据，有则删除
        count = permissionMapper.queryCountByMoudleId(mId);
        if(count > 0){
            AssertUtil.isTrue(permissionMapper.deletePermissionByMoudleId(mId) != count ,"权限删除失败");
        }

        //删除资源
        AssertUtil.isTrue(moduleMapper.deleteModuleByMid(mId) < 1,"资源删除失败");
    }
}
