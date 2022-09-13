package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    //根据角色ID 查询对应的权限个数
    Integer selectCountPermission(Integer roleId);

    //根据角色ID 删除对应的权限
    Integer deletePermissionByRoleId(Integer roleId);

    //查询当前角色拥有的资源
    List<Integer> selectPermissionByRoleId(Integer roleId);

    //根据登录用户的id查询对应的权限
    List<Integer> selectAclvalueByUserId(int id);

    //通过模块id查询关联的权限数据
    Integer queryCountByMoudleId(Integer mId);

    //删除某个模块关联的所有权限数据
    Integer deletePermissionByMoudleId(Integer mId);
}