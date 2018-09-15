package com.cn.layui.sysadmin.dao;


import com.cn.layui.sysadmin.entity.SysResumeRecommendCompany;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysResumeRecommendCompanyMapper {

    int getPushRecord(SysResumeRecommendCompany record);

    int insertSelective(SysResumeRecommendCompany record);


}