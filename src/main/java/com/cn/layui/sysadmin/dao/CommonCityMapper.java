package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.entity.CommonCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonCityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommonCity record);

    int insertSelective(CommonCity record);

    CommonCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommonCity record);

    int updateByPrimaryKey(CommonCity record);

    List<CommonCity> getAllCity();
}