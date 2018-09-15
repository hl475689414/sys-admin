package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.vo.PostPositionVo;
import com.cn.layui.sysadmin.vo.RecruitJobBaseListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecruitJobBaseMapper {


    List<RecruitJobBaseListVo> getJobBaseList(Integer companyId);


    List<PostPositionVo> selectListByCondition(@Param("classId") Integer classId, @Param("workCity") Integer workCity, @Param("workYear") Integer workYear, @Param("rank") Integer rank, @Param("sort") Integer sort
    , @Param("curr")Integer curr,@Param("keys") String keys);

    Integer getCountByCondition(@Param("classId") Integer classId, @Param("workCity")Integer workCity,@Param("workYear") Integer workYear, @Param("rank") Integer rank,@Param("keys") String keys);

    int updateRecommendNumByPrimaryKey(Integer id);
}