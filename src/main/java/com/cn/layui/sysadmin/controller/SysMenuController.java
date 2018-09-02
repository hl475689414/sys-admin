package com.cn.layui.sysadmin.controller;

import com.cn.layui.sysadmin.service.SystemMenuService;
import com.cn.layui.sysadmin.utils.JsonResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class SysMenuController {
    @Resource
    private SystemMenuService systemMenuService;

    /**
     * 获取平台菜单列表
     * @return
     */
    @RequestMapping("/getSysMenuList")
    @ResponseBody
   // @RequiresPermissions("sys:sysMenu:menuList")
    public String getSysMenuList() {
        List<Object> sysMenuList = systemMenuService.getSysMenuList();
        System.out.println(sysMenuList);
        JSONArray jsonArray = JSONArray.fromObject(sysMenuList);
        String replace = jsonArray.toString().replace("=", ":");
        System.out.println(replace);
        return replace;
    }
}
