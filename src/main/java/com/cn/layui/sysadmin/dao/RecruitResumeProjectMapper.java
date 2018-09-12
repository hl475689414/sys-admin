package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.vo.RecruitResumeProjectListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecruitResumeProjectMapper {

    List<RecruitResumeProjectListVo> getResumeProjectList(Integer userId);
}