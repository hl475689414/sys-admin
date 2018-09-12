package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.vo.RecruitResumeEducationListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecruitResumeEducationMapper {
    List<RecruitResumeEducationListVo> getResumeEducationList(Integer userId);
}