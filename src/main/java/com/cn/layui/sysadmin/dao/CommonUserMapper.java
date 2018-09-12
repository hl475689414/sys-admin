package com.cn.layui.sysadmin.dao;

import com.cn.layui.sysadmin.entity.CommonUser;
import com.cn.layui.sysadmin.vo.CommonUserListVo;
import com.cn.layui.sysadmin.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonUserMapper {
    List<CommonUserListVo> getUserListPage(@Param(value = "keys") String keys, @Param(value = "sex") Integer sex, @Param(value = "state") Integer state, @Param(value = "isCompany") Integer isCompany,
                                           @Param(value = "start") Integer start ,@Param(value = "end") Integer end);

    Map<String,Long> selectCount(@Param(value = "keys") String keys, @Param(value = "sex") Integer sex, @Param(value = "state") Integer state, @Param(value = "isCompany") Integer isCompany);

    UserInfoVo getUserInfo(Integer userId);

    CommonUser selectByPrimaryKey(Integer userId);
}
