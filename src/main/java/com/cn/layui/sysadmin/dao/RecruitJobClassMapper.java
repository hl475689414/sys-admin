package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.entity.RecruitJobClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecruitJobClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecruitJobClass record);

    int insertSelective(RecruitJobClass record);

    RecruitJobClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecruitJobClass record);

    int updateByPrimaryKey(RecruitJobClass record);

    List<RecruitJobClass> getAllJobClass();
}