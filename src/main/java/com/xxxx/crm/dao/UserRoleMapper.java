package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    //获取某个用户对应的角色数量
    Integer countUserRole(Integer id);

    //删除某个用户下所有角色
    Integer deleteUserRoleByUid(Integer id);
}