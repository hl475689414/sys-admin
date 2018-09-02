package com.cn.layui.sysadmin.service;

import com.cn.layui.sysadmin.entity.SystemMenu;
import com.cn.layui.sysadmin.utils.JsonResult;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理-业务逻辑层
 */
public interface SystemMenuService {

    /**
     * 获取平台菜单列表
     * @return
     */
    List<Object> getSysMenuList();


    /**
     * 获取用户角色权限列表
     * @param userId
     * @return
     */
    Set<String> listPerms(int userId);
}
