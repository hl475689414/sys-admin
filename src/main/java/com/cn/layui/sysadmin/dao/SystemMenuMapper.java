package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.entity.SystemMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemMenuMapper {

    List<SystemMenu> getAllMenuList();

    List<String> listUserPerms(Integer userId);
}
