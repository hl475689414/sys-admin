package com.cn.layui.sysadmin.service.impl;

import com.cn.layui.sysadmin.dao.*;
import com.cn.layui.sysadmin.entity.CommonUser;
import com.cn.layui.sysadmin.entity.RecruitCompanyVideo;
import com.cn.layui.sysadmin.entity.RecruitResumeVideo;
import com.cn.layui.sysadmin.service.UserManageService;
import com.cn.layui.sysadmin.utils.DateUtil;
import com.cn.layui.sysadmin.utils.DateUtils;
import com.cn.layui.sysadmin.utils.JsonResult;
import com.cn.layui.sysadmin.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserManageServiceImpl extends BaseService implements UserManageService {
    @Autowired
    private CommonUserMapper commonUserMapper;
    @Autowired
    private RecruitResumeMapper recruitResumeMapper;
    @Autowired
    private RecruitJobFilterMapper recruitJobFilterMapper;
    @Autowired
    private RecruitResumeVideoMapper recruitResumeVideoMapper;
    @Autowired
    private RecruitResumeWorkMapper recruitResumeWorkMapper;
    @Autowired
    private RecruitResumeProjectMapper recruitResumeProjectMapper;
    @Autowired
    private RecruitResumeEducationMapper recruitResumeEducationMapper;
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private CommonCompanyMapper commonCompanyMapper;
    @Autowired
    private RecruitCompanyVideoMapper recruitCompanyVideoMapper;
    @Autowired
    private RecruitJobBaseMapper recruitJobBaseMapper;

    /**
     * 获取用户列表
     * @param keys
     * @param sex
     * @param state
     * @param isCompany
     * @return
     */
    @Override
    public JsonResult getCommonUserList(String keys, Integer sex, Integer state, Integer isCompany,Integer curr,Integer limit) {
        Integer start =(curr-1)*limit;
        Integer end=start+limit;
        List<CommonUserListVo> list = commonUserMapper.getUserListPage(keys, sex, state, isCompany,start,end);
        for(CommonUserListVo userVo : list) {
            userVo.setLoginTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(userVo.getLoginTime()), "yyyy-MM-dd HH:mm"));
            userVo.setRegisterTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(userVo.getRegisterTime()), "yyyy-MM-dd HH:mm"));
           int is=1;//表示企业
            if(empty(userVo.getCompanyName())) {
                userVo.setCompanyName("无");
                is=0;//表示个人
            }
            if(userVo.getState()==0){
               userVo.setStateName("正常");
            }else{
                userVo.setStateName("关闭");
            }
            userVo.setButt("<button class=\"layui-btn userDetail\" id="+userVo.getId()+" isCompany="+is+">用户详情</button>");
        }
        return new JsonResult(0, "获取成功", list.size(), list);
    }

    @Override
    public Integer selectCount(String keys, Integer sex, Integer state, Integer isCompany) {
        Integer result=0;
        Long dataCount = commonUserMapper.selectCount(keys, sex, state, isCompany).get("dataCount");
        if(dataCount!=null){
           result= Integer.valueOf(dataCount+"");
        }
        return result;
    }

    @Override
    public JsonResult getUserInfo(Integer userId) {
        UserInfoVo userInfoVo = commonUserMapper.getUserInfo(userId);
        userInfoVo.setLoginTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(userInfoVo.getLoginTime()), "yyyy-MM-dd HH:mm"));
        userInfoVo.setRegisterTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(userInfoVo.getRegisterTime()), "yyyy-MM-dd HH:mm"));
        return new JsonResult(0, "获取成功", 0, userInfoVo);
    }

    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    @Override
    public JsonResult getPersonalUserInfo(Integer userId) {
        PersonalUserInfoVo personalUserInfoVo = recruitResumeMapper.getPersonalUserInfo(userId);
        if(empty(personalUserInfoVo)) {
            return new JsonResult(1, "暂无个人身份信息");
        }
        RecruitResumeVideo recruitResumeVideo = recruitResumeVideoMapper.selectByPrimaryKey(userId);
        if(notEmpty(recruitResumeVideo)) {
            personalUserInfoVo.setResumeVideoUrl(recruitResumeVideo.getUrl());
        }

        List<RecruitJobFilterListVo> jobFilterListVos = recruitJobFilterMapper.getJobFilterList(userId);
        personalUserInfoVo.setJobFilterListVos(jobFilterListVos);

        List<RecruitResumeWorkListVo> resumeWorkListVos = recruitResumeWorkMapper.getResumeWorkList(userId);
        for(RecruitResumeWorkListVo recruitResumeWorkListVo : resumeWorkListVos) {
            recruitResumeWorkListVo.setBeginTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(recruitResumeWorkListVo.getBeginTime()), "yyyy-MM-dd"));
            recruitResumeWorkListVo.setEndTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(recruitResumeWorkListVo.getEndTime()), "yyyy-MM-dd"));
        }
        personalUserInfoVo.setResumeWorkListVos(resumeWorkListVos);

        List<RecruitResumeProjectListVo> resumeProjectListVos = recruitResumeProjectMapper.getResumeProjectList(userId);
        personalUserInfoVo.setResumeProjectListVos(resumeProjectListVos);

        List<RecruitResumeEducationListVo> resumeEducationListVos = recruitResumeEducationMapper.getResumeEducationList(userId);
        for(RecruitResumeEducationListVo recruitResumeEducationListVo : resumeEducationListVos) {
            recruitResumeEducationListVo.setEducationName(configMapper.selectByCodeTypes(recruitResumeEducationListVo.getEducation()+"", 1).getValue());
            recruitResumeEducationListVo.setBeginTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(recruitResumeEducationListVo.getBeginTime()), "yyyy-MM-dd"));
            recruitResumeEducationListVo.setEndTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(recruitResumeEducationListVo.getEndTime()), "yyyy-MM-dd"));
        }
        personalUserInfoVo.setResumeEducationListVos(resumeEducationListVos);
        return new JsonResult(0, "获取成功", 0, personalUserInfoVo);
    }


    /**
     * 获取企业信息
     * @param userId
     * @return
     */
    @Override
    public JsonResult getCompanyUserInfo(Integer userId) {
        CommonUser commonUser = commonUserMapper.selectByPrimaryKey(userId);
        if(empty(commonUser)) {
            return new JsonResult(1, "获取失败");
        }
        if(commonUser.getCompanyid() == 0) {
            return new JsonResult(1, "暂无企业身份信息");
        }
        CompanyUserInfoVo companyUserInfoVo = commonCompanyMapper.getCompanyUserInfoVo(commonUser.getId());
        if(notEmpty(companyUserInfoVo)) {
            companyUserInfoVo.setUserId(userId);
            RecruitCompanyVideo recruitCompanyVideo = recruitCompanyVideoMapper.selectByPrimaryKey(commonUser.getCompanyid());
            if(notEmpty(recruitCompanyVideo)) {
                companyUserInfoVo.setJobVideoUrl(recruitCompanyVideo.getUrl());
            }

            List<RecruitJobBaseListVo> recruitJobBaseListVos = recruitJobBaseMapper.getJobBaseList(commonUser.getCompanyid());
            for(RecruitJobBaseListVo recruitJobBaseListVo : recruitJobBaseListVos) {
                recruitJobBaseListVo.setWorkYearTitle(configMapper.selectByCodeTypes(recruitJobBaseListVo.getWorkYear()+"", 4).getValue());
                recruitJobBaseListVo.setEducationTitle(configMapper.selectByCodeTypes(recruitJobBaseListVo.getEducation()+"", 1).getValue());
            }
            companyUserInfoVo.setRecruitJobBaseListVos(recruitJobBaseListVos);
            return new JsonResult(0, "获取成功", 0, companyUserInfoVo);
        }else {
            return new JsonResult(1, "暂无企业身份信息");
        }
    }
}
