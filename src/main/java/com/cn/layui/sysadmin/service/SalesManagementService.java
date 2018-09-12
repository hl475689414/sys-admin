package com.cn.layui.sysadmin.service;

import com.cn.layui.sysadmin.utils.JsonResult;

/**
 * 销售管理-业务逻辑层
 */
public interface SalesManagementService {

    /**
     * 通过条件查找企业发布职位列表
     * @param classId
     * @param workCity
     * @param workYear
     * @param rank
     * @param sort
     */
    JsonResult selectListByCondition(Integer classId, Integer workCity, Integer workYear, Integer rank, Integer sort,Integer curr,Integer limit,String keys);

    /**
     * 通过条件查找数据总数
     * @param classId
     * @param workCity
     * @param workYear
     * @param rank
     * @param keys
     * @return
     */
    Integer getCountByCondition(Integer classId, Integer workCity, Integer workYear, Integer rank, String keys);

    /**
     * 获取求职意向列表
     * @param keys
     * @param classId
     * @param workCity
     * @param workYear
     * @param ispush
     * @param curr
     * @param limit
     * @return
     */
    JsonResult getJobWantedInfos(String keys, Integer classId, Integer workCity, Integer workYear, Integer ispush, Integer curr, Integer limit);


    /**
     * 获取选择人才总数据量
     * @param classId
     * @param workYear
     * @param workCity
     * @param ispush
     * @param keys
     * @return
     */
    Integer getChooseCount(Integer classId, Integer workYear, Integer workCity, Integer ispush, String keys);
}
