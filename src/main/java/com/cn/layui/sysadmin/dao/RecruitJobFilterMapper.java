package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.vo.JobWantedInfoVo;
import com.cn.layui.sysadmin.vo.RecruitJobFilterListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecruitJobFilterMapper {

    List<RecruitJobFilterListVo> getJobFilterList(Integer userId);

    List<JobWantedInfoVo> getListByCondition(@Param("keys") String keys,@Param("classId") Integer classId,@Param("workCity") Integer workCity,@Param("startWorkYear") Integer startWorkYear,@Param("endWorkYear") Integer endWorkYear,@Param("ispush") Integer ispush,@Param("curr") Integer curr);

    Integer getChooseCount(@Param("classId") Integer classId,@Param("startWorkYear") Integer startWorkYear,@Param("endWorkYear") Integer endWorkYear,@Param("workCity") Integer workCity,@Param("ispush") Integer ispush,@Param("keys") String keys);
}