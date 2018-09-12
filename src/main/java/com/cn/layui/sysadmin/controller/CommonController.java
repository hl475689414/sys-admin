package com.cn.layui.sysadmin.controller;

import com.cn.layui.sysadmin.service.CommonService;
import com.cn.layui.sysadmin.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公共控制器
 */
@Controller
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private CommonService commonService;
    /**
     * 获取所有职位分类
     * @return
     */
    @RequestMapping(value = "/getJobClassList")
    @ResponseBody
    public JsonResult getJobClassList() {
        return commonService.getJobClassList();
    }

    /**
     * 获取所有城市分类
     * @return
     */
    @RequestMapping(value = "/getCityList")
    @ResponseBody
    public JsonResult getCityList() {
        return commonService.getCityList();
    }

}
