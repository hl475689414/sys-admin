package com.cn.layui.sysadmin.service.impl;

import com.cn.layui.sysadmin.dao.RecruitJobBaseMapper;
import com.cn.layui.sysadmin.dao.RecruitJobFilterMapper;
import com.cn.layui.sysadmin.service.SalesManagementService;
import com.cn.layui.sysadmin.utils.DateUtil;
import com.cn.layui.sysadmin.utils.DateUtils;
import com.cn.layui.sysadmin.utils.JsonResult;
import com.cn.layui.sysadmin.vo.JobWantedInfoVo;
import com.cn.layui.sysadmin.vo.PostPositionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SalesManagementServiceImpl implements SalesManagementService {
    @Autowired
    private RecruitJobBaseMapper recruitJobBaseMapper;
    @Autowired
    private RecruitJobFilterMapper recruitJobFilterMapper;

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
            postPositionVo.setButton("<button class=\"layui-btn choose\" classId="+postPositionVo.getClassId()+" companyId="+postPositionVo.getCompanyId()+" companyName="+postPositionVo.getCompanyName()+" className="+postPositionVo.getWorkName()+">选择</button>");
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
            info.setCheck("<input type=\"checkbox\" name=\"isCheck\" lay-skin=\"primary\" userid=\""+info.getId()+"\">");
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
}
