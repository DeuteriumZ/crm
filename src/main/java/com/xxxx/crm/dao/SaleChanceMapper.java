package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.vo.SaleChance;

import java.util.List;
import java.util.Map;

public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {

    //多条件分页查询数据
    public List<SaleChance> queryByParams(SaleChanceQuery query);
    
    //查询所有销售人员数据
    public List<Map<String,Object>> queryAllSales();

    //查询所有销售人员数据
    public Integer deleteBatch(Integer[] ids);

    //更新开发状态
    public Integer updateDevResult(Integer id, Integer devResult);
    
}