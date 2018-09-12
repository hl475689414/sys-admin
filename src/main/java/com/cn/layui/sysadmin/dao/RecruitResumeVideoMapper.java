package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.entity.RecruitResumeVideo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecruitResumeVideoMapper {
    RecruitResumeVideo selectByPrimaryKey(Integer userid);

}