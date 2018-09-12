package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ConfigMapper {


    Config selectByCodeTypes(@Param(value = "code") String code, @Param(value = "types") Integer types);

}