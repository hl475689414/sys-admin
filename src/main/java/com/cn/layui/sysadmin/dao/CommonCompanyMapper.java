package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.vo.CompanyUserInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonCompanyMapper {
    CompanyUserInfoVo getCompanyUserInfoVo(Integer userId);
}