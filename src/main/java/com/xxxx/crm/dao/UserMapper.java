package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.vo.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User,Integer> {
    //通过用户名称查询数据
    public User queryUserByName(String name);

    //多条件分页查询
    public List<User> queryUserBYParams(UserQuery query);

    //批量删除
    public Integer deleteUsers(Integer[] ids);
  
}