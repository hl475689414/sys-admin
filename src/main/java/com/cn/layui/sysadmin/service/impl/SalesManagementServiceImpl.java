package com.cn.layui.sysadmin.service.impl;

import com.cn.layui.sysadmin.dao.CommonUserMapper;
import com.cn.layui.sysadmin.dao.RecruitJobBaseMapper;
import com.cn.layui.sysadmin.dao.RecruitJobFilterMapper;
import com.cn.layui.sysadmin.dao.SysResumeRecommendCompanyMapper;
import com.cn.layui.sysadmin.entity.PushUser;
import com.cn.layui.sysadmin.entity.SysResumeRecommendCompany;
import com.cn.layui.sysadmin.service.SalesManagementService;
import com.cn.layui.sysadmin.utils.DateUtil;
import com.cn.layui.sysadmin.utils.DateUtils;
import com.cn.layui.sysadmin.utils.JsonResult;
import com.cn.layui.sysadmin.utils.easemob.pushUtil;
import com.cn.layui.sysadmin.vo.JobWantedInfoVo;
import com.cn.layui.sysadmin.vo.PostPositionVo;
import com.cn.layui.sysadmin.vo.PushResult;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesManagementServiceImpl implements SalesManagementService {
    @Autowired
    private RecruitJobBaseMapper recruitJobBaseMapper;
    @Autowired
    private RecruitJobFilterMapper recruitJobFilterMapper;
    @Autowired
    private SysResumeRecommendCompanyMapper sysResumeRecommendCompanyMapper;
    @Autowired
    private CommonUserMapper commonUserMapper;
   @Autowired
    private pushUtil pushUtil;

    @Override
    public JsonResult selectListByCondition(Integer classId, Integer workCity, Integer workYear, Integer rank, Integer sort,Integer curr,Integer limit,String keys) {
        Integer start =(curr-1)*limit;
        List<PostPositionVo> postPositionVos = recruitJobBaseMapper.selectListByCondition(classId, workCity, workYear, rank, sort,start,keys);
        for (PostPositionVo postPositionVo:postPositionVos) {
            if(postPositionVo.getLastLoginTime()!=null){
                postPositionVo.setLastLoginTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(postPositionVo.getLastLoginTime()), "yyyy-MM-dd HH:mm"));
            }
            if(postPositionVo.getReleaseTime()!=null && !postPositionVo.getReleaseTime().equals("")){
                postPositionVo.setReleaseTime(DateUtils.formatDate(new Date(Long.parseLong(postPositionVo.getReleaseTime())), "yyyy-MM-dd HH:mm"));
            }
            postPositionVo.setButton("<button class=\"layui-btn choose\" classId="+postPositionVo.getClassId()+" companyId="+postPositionVo.getCompanyId()+" companyName="+postPositionVo.getCompanyName()+" className="+postPositionVo.getWorkName()+ " uid="+postPositionVo.getUid()+">选择</button>");
        }
        JsonResult result=new JsonResult(0, "成功", postPositionVos.size(), postPositionVos);
        return result;
    }

    @Override
    public Integer getCountByCondition(Integer classId, Integer workCity, Integer workYear, Integer rank, String keys) {
        return recruitJobBaseMapper.getCountByCondition(classId,workCity,workYear,rank,keys);
    }

    @Override
    public JsonResult getJobWantedInfos(String keys, Integer classId, Integer workCity, Integer workYear, Integer ispush, Integer curr, Integer limit) {
        Integer start =(curr-1)*limit;
        Integer startWorkYear=null;
        Integer endWorkYear=null;
        String now = new SimpleDateFormat("yyyy").format(new Date());
        Integer nowYear=Integer.valueOf(now);
        if(workYear!=null){
            if(workYear==2){//1年以下
                startWorkYear=nowYear;
            }
            if(workYear==3){//1-3年
                endWorkYear=nowYear-1;
                startWorkYear=nowYear-3;
            }
            if(workYear==4){//3-5年
                startWorkYear=nowYear-5;
                endWorkYear=nowYear-3;
            }
            if(workYear==5){//5-10年
                startWorkYear=nowYear-10;
                endWorkYear=nowYear-5;
            }
            if(workYear==6){//10年以上
                endWorkYear=nowYear-10;
            }
        }
        List<JobWantedInfoVo> lists=recruitJobFilterMapper.getListByCondition(keys,classId,workCity,startWorkYear,endWorkYear,ispush,start);
        for (JobWantedInfoVo info:lists) {
            if(info.getWorkYear()!=null){
                info.setWorkYear((nowYear-Integer.valueOf(info.getWorkYear()))+" 年");
            }
            if(info.getLastLoginTime()!=null){
                info.setLastLoginTime(DateUtils.formatDate(DateUtil.toDefaultDateTime(info.getLastLoginTime()), "yyyy-MM-dd HH:mm"));
            }
            info.setCheck("<input type=\"checkbox\" name=\"isCheck\" lay-skin=\"primary\" userid=\""+info.getId()+"\" filterId=\""+info.getFilterId()+"\" >");
        }
        return new JsonResult(0, "成功", lists.size(), lists);
    }

    @Override
    public Integer getChooseCount(Integer classId, Integer workYear, Integer workCity, Integer ispush, String keys) {
        Integer startWorkYear=null;
        Integer endWorkYear=null;
        String now = new SimpleDateFormat("yyyy").format(new Date());
        Integer nowYear=Integer.valueOf(now);
        if(workYear!=null) {
            if (workYear == 2) {//1年以下
                startWorkYear = nowYear;
            }
            if (workYear == 3) {//1-3年
                endWorkYear = nowYear - 1;
                startWorkYear = nowYear - 3;
            }
            if (workYear == 4) {//3-5年
                startWorkYear = nowYear - 5;
                endWorkYear = nowYear - 3;
            }
            if (workYear == 5) {//5-10年
                startWorkYear = nowYear - 10;
                endWorkYear = nowYear - 5;
            }
            if (workYear == 6) {//10年以上
                endWorkYear = nowYear - 10;
            }
        }
        return recruitJobFilterMapper.getChooseCount(classId, startWorkYear, endWorkYear, workCity, ispush, keys);
    }

    @Override
    public JsonResult toDoPush(String companyUserId, String[] ids, String[] fIds, String classId) {
        Integer successCount=0;//成功数量
        Integer failCount=0;//失败数量
        for(int i=1;i<ids.length;i++){
            SysResumeRecommendCompany sysResumeRecommendCompany=new SysResumeRecommendCompany();
            sysResumeRecommendCompany.setRecommenduserid(Integer.valueOf(companyUserId));
            sysResumeRecommendCompany.setJobbaseid(Integer.valueOf(classId));
            sysResumeRecommendCompany.setFilterid(Integer.valueOf(fIds[i]));
            sysResumeRecommendCompany.setUserid(Integer.valueOf(ids[i]));
            sysResumeRecommendCompany.setCreatetime(new Date());
            int count = sysResumeRecommendCompanyMapper.getPushRecord(sysResumeRecommendCompany);
            if(count == 0) {//判断是否已推送过
                PushUser pushUser=commonUserMapper.selectPushUserByPrimaryKey(Integer.valueOf(ids[i]));
                boolean b = pushUtil.pushUserIdToCompanyUserId(pushUser, Integer.valueOf(companyUserId));//推送
                if(b){
                    successCount++;
                }
                sysResumeRecommendCompanyMapper.insertSelective(sysResumeRecommendCompany);
               // 累加求职意向推送次数
                recruitJobFilterMapper.updateRecommendNum(sysResumeRecommendCompany.getFilterid());
                //累加招聘职位已被推送次数
                recruitJobBaseMapper.updateRecommendNumByPrimaryKey(sysResumeRecommendCompany.getJobbaseid());
            }else{
                failCount++;
            }
        }
        PushResult pushResult=new PushResult();
        pushResult.setFailCount(failCount);
        pushResult.setSuccessCount(successCount);
        pushResult.setSumCount(ids.length-1);
        System.out.println("成功数量："+successCount+"---失败数量："+failCount+"---总数量："+(ids.length-1));
        return new JsonResult(0, "成功", 3, pushResult);
    }

}
