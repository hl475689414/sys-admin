package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.vo.RecruitResumeWorkListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecruitResumeWorkMapper {
    List<RecruitResumeWorkListVo> getResumeWorkList(Integer userId);
}