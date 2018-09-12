package com.cn.layui.sysadmin.service;

import com.cn.layui.sysadmin.utils.JsonResult;

/**
 * 用户管理-业务逻辑层
 */
public interface UserManageService {
    /**
     * 分页获取用户列表
     * @param keys
     * @param sex
     * @param state
     * @param isCompany
     * @param curr 当前页
     * @param limit 每页数据条数
     * @return
     */
    JsonResult getCommonUserList(String keys, Integer sex, Integer state, Integer isCompany,Integer curr,Integer limit);

    /**
     * 通过查询条件查总条数
     * @param keys
     * @param sex
     * @param state
     * @param isCompany
     * @return
     */
    Integer selectCount(String keys, Integer sex, Integer state, Integer isCompany);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    JsonResult getUserInfo(Integer userId);


    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    JsonResult getPersonalUserInfo(Integer userId);

    /**
     * 获取企业信息
     * @param userId
     * @return
     */
    JsonResult getCompanyUserInfo(Integer userId);
}
