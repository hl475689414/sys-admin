package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.vo.PersonalUserInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecruitResumeMapper {
    PersonalUserInfoVo getPersonalUserInfo(Integer userId);
}