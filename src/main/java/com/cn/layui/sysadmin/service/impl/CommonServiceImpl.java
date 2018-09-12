package com.cn.layui.sysadmin.service.impl;


import com.cn.layui.sysadmin.dao.CommonBusinessMapper;
import com.cn.layui.sysadmin.dao.CommonCityMapper;
import com.cn.layui.sysadmin.dao.RecruitJobClassMapper;
import com.cn.layui.sysadmin.entity.CommonBusiness;
import com.cn.layui.sysadmin.entity.CommonCity;
import com.cn.layui.sysadmin.entity.RecruitJobClass;
import com.cn.layui.sysadmin.entity.SystemUser;
import com.cn.layui.sysadmin.service.CommonService;
import com.cn.layui.sysadmin.utils.JsonResult;
import com.cn.layui.sysadmin.vo.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李怀鹏 on 2018/5/14.
 */
@Service("commonService")
public class CommonServiceImpl extends BaseService implements CommonService {
    @Autowired
    private RecruitJobClassMapper recruitJobClassMapper;
    @Autowired
    private CommonBusinessMapper commonBusinessMapper;
    @Autowired
    private CommonCityMapper commonCityMapper;

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    @Override
    public JsonResult login(String account, String password) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            SystemUser user = (SystemUser) subject.getPrincipal();
            Map map = new HashMap();
            map.put("token",subject.getSession().getId()); //返回sessionId做token
            map.put("sysUserId", user.getId());
            map.put("trueNmae", user.getUserName());
            return new JsonResult(0, "登录成功", 0, map);
        } catch (Exception e) {
            return new JsonResult(1, e.getMessage());
        }
    }

    /**
     * 获取所有职位分类
     * @return
     */
    @Override
    public JsonResult getJobClassList() {
        List<RecruitJobClass> list = recruitJobClassMapper.getAllJobClass();
        List<JobClassListVo> jobList = new ArrayList<>();
        JobClassListVo jobClassListVo = null;
        for(RecruitJobClass recruitJobClass : list) {
            if(recruitJobClass.getId() < 1000) {
                jobClassListVo = new JobClassListVo();
                jobClassListVo.setValue(recruitJobClass.getId());
                jobClassListVo.setLabel(recruitJobClass.getTitle());
                jobList.add(jobClassListVo);
            }
        }

        List<JobClassChildrenVo> childrenList = null;
        JobClassChildrenVo jobClassChildrenVo = null;
        int start = 0;
        int end = 0;
        for(JobClassListVo jobClassListVo1 : jobList) {
            childrenList = new ArrayList<>();
//            start = jobClassListVo1.getId() * 1000;
            start=jobClassListVo1.getValue()*1000;
//            end = jobClassListVo1.getId() * 1000 + 999;
              end = jobClassListVo1.getValue() * 1000 + 999;
            for(RecruitJobClass recruitJobClass : list) {
                if(recruitJobClass.getId() > start && recruitJobClass.getId() < end) {
                    jobClassChildrenVo = new JobClassChildrenVo();
                    jobClassChildrenVo.setValue(recruitJobClass.getId());
                    jobClassChildrenVo.setLabel(recruitJobClass.getTitle());
                    childrenList.add(jobClassChildrenVo);
                }
            }
            jobClassListVo1.setChildren(childrenList);
        }
        return new JsonResult(0, "获取成功", jobList.size(), jobList);
    }

    /**
     * 获取所有行业分类
     * @return
     */
    @Override
    public JsonResult getBusinessList() {
        List<CommonBusiness> list = commonBusinessMapper.getAllBusiness();
        List<CommonBusinessListVo> businessList = new ArrayList<>();
        CommonBusinessListVo commonBusinessListVo = null;
        for(CommonBusiness commonBusiness : list) {
            if(commonBusiness.getParentid() == 0) {
                commonBusinessListVo = new CommonBusinessListVo();
                commonBusinessListVo.setId(commonBusiness.getId());
                commonBusinessListVo.setTitle(commonBusiness.getName());
                businessList.add(commonBusinessListVo);
            }
        }

        List<CommonBusinessChildrenVo> childrenVos = null;
        CommonBusinessChildrenVo commonBusinessChildrenVo = null;
        for(CommonBusinessListVo commonBusinessListVo1 : businessList) {
            childrenVos = new ArrayList<>();
            for(CommonBusiness commonBusiness : list) {
                if(commonBusinessListVo1.getId() == commonBusiness.getParentid()) {
                    commonBusinessChildrenVo = new CommonBusinessChildrenVo();
                    commonBusinessChildrenVo.setId(commonBusiness.getId());
                    commonBusinessChildrenVo.setTitle(commonBusiness.getName());
                    childrenVos.add(commonBusinessChildrenVo);
                }
            }
            commonBusinessListVo1.setChildrenList(childrenVos);
        }
        return new JsonResult(0, "获取成功", businessList.size(), businessList);
    }

    /**
     * 获取所有城市分类
     * @return
     */
    @Override
    public JsonResult getCityList() {
        List<CommonCity> list = commonCityMapper.getAllCity();
        List<CommonCityListVo> cityList = new ArrayList<>();
        CommonCityListVo commonCityListVo = null;
        for(CommonCity commonCity : list) {
            if(commonCity.getId() < 1000) {
                commonCityListVo = new CommonCityListVo();
                commonCityListVo.setValue(commonCity.getId());
                commonCityListVo.setLabel(commonCity.getName());
                cityList.add(commonCityListVo);
            }
        }

        List<CommonCityChildrenVo> childrenVos = null;
        CommonCityChildrenVo commonCityChildrenVo = null;
        int start = 0;
        int end = 0;
        for(CommonCityListVo commonCityListVo1 : cityList) {
            childrenVos = new ArrayList<>();
            start = commonCityListVo1.getValue() * 1000;
            end = commonCityListVo1.getValue() * 1000 + 999;
            for(CommonCity commonCity : list) {
                if(commonCity.getId() > start && commonCity.getId() < end) {
                    commonCityChildrenVo = new CommonCityChildrenVo();
                    commonCityChildrenVo.setValue(commonCity.getId());
                    commonCityChildrenVo.setLabel(commonCity.getName());
                    childrenVos.add(commonCityChildrenVo);
                }
            }
            commonCityListVo1.setChildren(childrenVos);
        }
        return new JsonResult(0, "获取成功", cityList.size(), cityList);
    }


}
