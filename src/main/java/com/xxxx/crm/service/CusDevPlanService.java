package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.CusDevPlanMapper;
import com.xxxx.crm.dao.SaleChanceMapper;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Resource
    private CusDevPlanMapper cusDevPlanMapper;

    @Resource
    private SaleChanceMapper saleChanceMapper;

    /*多条件分页查询客户计划表*/
    public Map<String, Object> queryByParams(CusDevPlanQuery query){
        Map<String, Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<CusDevPlan> cusDevPlans = cusDevPlanMapper.queryByParams(query);
        //按照分页条件，格式化数据
        PageInfo<CusDevPlan> cusDevPlanPageInfo = new PageInfo<>(cusDevPlans);

        map.put("code",0);
        map.put("msg","");
        map.put("count",cusDevPlanPageInfo.getTotal());
        map.put("data",cusDevPlanPageInfo.getList());
        return map;
    }

    /**
     * 添加数据
     *      1.数据校验
     *          saleChanceId   营销机会的id  非空 / 数据存在
     *          计划内容         非空
     *          计划时间         非空
     *      2.设置默认值
     *          is_valid     数据是否有效
     *          create_date
     *          update_date
     *      3.执行添加操作，判断
     */
    @Transactional
    public void addCusDevPan(CusDevPlan cusDevPlan){
        // 数据校验
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        //设置默认值
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        //执行添加操作，判断
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan) < 1,"计划添加失败");
    }



    /**
     * 修改数据
     *      1.数据校验
     *          计划项 id       非空/数据存在
     *          saleChanceId   营销机会的id  非空 / 数据存在
     *          计划内容         非空
     *          计划时间         非空
     *      2.设置默认值
     *          update_date
     *      3.执行添加操作，判断
     */
    @Transactional
    public void updateCusDevPan(CusDevPlan cusDevPlan){
        AssertUtil.isTrue(cusDevPlan.getId() == null || null == cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId()),"计划项数据异常");
        // 数据校验
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        //设置默认值
        cusDevPlan.setUpdateDate(new Date());
        //执行添加操作，判断
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) < 1,"计划修改失败");
    }



    /**
     * 校验添加和修改的数据
     *      saleChanceId   营销机会的id  非空 / 数据存在
     *      计划内容         非空
     *      计划时间         非空
     * @param saleChanceId
     * @param planItem
     * @param planDate
     */
    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        AssertUtil.isTrue(null == saleChanceId ||
                null == saleChanceMapper.selectByPrimaryKey(saleChanceId),"营销机会数据不存在");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"计划内容不能为空");
        AssertUtil.isTrue(null == planDate,"计划日期不能为空");
    }

    /**
     * 删除时计划项
     * @param id
     */
    @Transactional
    public void delete(Integer id) {
        CusDevPlan cusDevPlan = cusDevPlanMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(id == null || null == cusDevPlan,"待删除计划项不存在");
        //设置数据失效
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) < 1,"计划项删除失败");
    }
}
