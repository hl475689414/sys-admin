package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.entity.RecruitCompanyVideo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecruitCompanyVideoMapper {


    RecruitCompanyVideo selectByPrimaryKey(Integer companyid);

}