package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.CookieUtil;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    /**
     * 多条件分页查询数据
     * @param saleChanceQuery
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryByParams(SaleChanceQuery saleChanceQuery,Integer flag,HttpServletRequest request){
        //判断lfag参数是否存在区分 客户开发计划和营销机会的页面
        if(flag != null && flag == 1){
            int id = LoginUserUtil.releaseUserIdFromCookie(request);
            saleChanceQuery.setAssignMan(id);
        }
        return saleChanceService.queryByParams(saleChanceQuery);
    }


    /**
     * 更新开发状态
     * @param id
     * @param devResult
     * @return
     */
    @PostMapping("updateDevResult")
    @ResponseBody
    public ResultInfo updateDevResult(Integer id,Integer devResult){
        saleChanceService.updateDevResult(id,devResult);
        return success();
    }



    /**
     * 打开营销机会管理页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }


    /**
     * 打开营销机会修改/添加的页面
     * @return
     */
    @RequestMapping("toAddUpdatePage")
    public String toAddUpdatePage(Integer id,HttpServletRequest request){
        //如果是修改操作那么需要将修改的数据映射在页面中
        if(id != null){
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            AssertUtil.isTrue(saleChance == null,"数据异常，请重试");
            request.setAttribute("saleChance",saleChance);
        }
        return "saleChance/add_update";
    }


    /**
     * 添加数据
     * @return
     */
    @PostMapping("save")
    @ResponseBody
    public ResultInfo save(HttpServletRequest request, SaleChance saleChance){
        //获取创建人
        String userName = CookieUtil.getCookieValue(request, "userName");
        saleChance.setCreateMan(userName);
        saleChanceService.addSlaChance(saleChance);
        return success();
    }


    /**
     * 添加数据
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success();
    }


    /**
     * 查询所有销售人员
     */
    @PostMapping("queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        return saleChanceService.queryAllSales();
    }


    /**
     * 逻辑删除
     * @param ids
     */
    @RequestMapping("deleteBatch")
    @ResponseBody
    public ResultInfo deleteBatchs(Integer[] ids){
        saleChanceService.deleteBatchs(ids);
        return success("删除成功");
    }
}
