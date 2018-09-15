package com.cn.layui.sysadmin.controller;

import com.cn.layui.sysadmin.service.CommonService;
import com.cn.layui.sysadmin.service.SalesManagementService;
import com.cn.layui.sysadmin.utils.JsonResult;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
@Controller
@RequestMapping(value = "SalesManagement")
public class SalesManagementController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommonService commonService;

    @Autowired
    private SalesManagementService salesManagementService;

    @RequestMapping("EnterprisePush")
    public String EnterprisePushIndex(Map<String,Object> map,Integer classId,Integer workCity,Integer workYear,Integer rank,String keys,Integer sort,Integer firstClassId,Integer firstWorkCity){
        JsonResult jobClassList = commonService.getJobClassList();
        String jobListString= JSONArray.fromObject(jobClassList.getData()).toString();
        map.put("jobClassList",jobListString);//所有职位（二级）
        JsonResult cityList = commonService.getCityList();
        String cityListString= JSONArray.fromObject(cityList.getData()).toString();
        map.put("cityList",cityListString);//所有城市（二级）
        Integer count = getDataCountByCondition(classId, workCity, workYear, rank, keys);
        map.put("dataCount",count);
        map.put("classId",classId);;
        map.put("firstClassId",firstClassId);
        map.put("workCity",workCity);
        map.put("firstWorkCity",firstWorkCity);
        map.put("workYear",workYear);
        map.put("rank",rank);
        map.put("sort",sort);
        map.put("keys",keys);
        return "/salesManagementent/erprisePush";
    }

    /**
     * 跳转人才选择页面
     * @param map
     * @param companyId  所选公司id
     * @param companyName  所选公司名
     * @param classId  职位id
     * @param className  职位名称
     * @return
     */
    @RequestMapping(value = "/personnelChoose")
    public String personnelChoose(Map<String,Object> map,Integer companyId,String companyName,Integer classId,String className,String keys,Integer workCity,Integer workYear,Integer firstClassId,Integer firstWorkCity,Integer push,Integer uid){
        JsonResult jobClassList = commonService.getJobClassList();
        String jobListString= JSONArray.fromObject(jobClassList.getData()).toString();
        map.put("jobClassList",jobListString);//所有职位（二级）
        JsonResult cityList = commonService.getCityList();
        String cityListString= JSONArray.fromObject(cityList.getData()).toString();
        map.put("cityList",cityListString);//所有城市（二级）
        map.put("companyId",companyId);
        map.put("companyName",companyName);
        map.put("classId",classId);
        map.put("firstClassId",firstClassId);
        map.put("className",className);
        map.put("firstWorkCity",firstWorkCity);
        map.put("workCity",workCity);
        map.put("keys",keys);
        map.put("push",push);
        map.put("workYear",workYear);
        map.put("uid",uid);
        map.put("dataCount",getChooseCount(keys,classId,workCity, workYear, push));
        return "/salesManagementent/personnelChoose";
    }



    @RequestMapping("test")
    public String test(){
        return "/salesManagementent/test";
    }

    /**
     * 获取招聘列表
     */
    @RequestMapping(value = "/getRecruitList")
    @ResponseBody
    public JsonResult getRecruitList(Integer classId,Integer workCity,Integer workYear,Integer rank,Integer sort,Integer curr,Integer limit,String keys){
        logger.debug("参数：=====》classId="+classId+",workCity="+workCity+",workYear="+workYear+",rank="+rank+",sort="+sort+",curr="+curr+",limit="+limit+",keys="+keys);
        if (curr ==null){
            curr=1;
        }
        if(limit==null){
            limit=15;
        }
        if(classId!=null&&classId==-1){
            classId=null;
        }
        if(workYear!=null&&workYear==-1){
            workYear=null;
        }
        if(workCity!=null&&workCity==-1){
            workCity=null;
        }
        if(rank!=null&&rank==-1){
            rank=null;
        }
        if(keys!=null&&keys.equals("")){
            keys=null;
        }
        JsonResult jsonResult = salesManagementService.selectListByCondition(classId, workCity, workYear, rank, sort, curr, limit,keys);
        return jsonResult;
    }

    /**
     * 通过条件查询数据总数
     * @param classId
     * @param workCity
     * @param workYear
     * @param rank
     * @param keys
     * @return
     */
    @RequestMapping("getCount")
    @ResponseBody
    public Integer getDataCountByCondition(Integer classId,Integer workCity,Integer workYear,Integer rank,String keys){
        if(classId!=null&&classId==-1){
            classId=null;
        }
        if(workYear!=null&&workYear==-1){
            workYear=null;
        }
        if(workCity!=null&&workCity==-1){
            workCity=null;
        }
        if(rank!=null&&rank==-1){
            rank=null;
        }
        if(keys!=null&&keys.equals("")){
            keys=null;
        }
        return salesManagementService.getCountByCondition(classId,workCity,workYear,rank,keys);
    }

    /**
     * 获取求职意向列表
     * @return
     */
    @RequestMapping(value = "/getJobWantedInfos")
    @ResponseBody
    public JsonResult getJobWantedInfos(String keys,Integer classId,Integer workCity,Integer workYear,Integer ispush,Integer curr,Integer limit){
        if (curr ==null){
            curr=1;
        }
        if(limit==null){
            limit=15;
        }
        if(classId!=null&&classId==-1){
            classId=null;
        }
        if(workYear!=null&&workYear==-1){
            workYear=null;
        }
        if(workCity!=null&&workCity==-1){
            workCity=null;
        }
        if(ispush!=null&&ispush==-1){
            ispush=null;
        }
        if(keys!=null&&keys.equals("")){
            keys=null;
        }
        return salesManagementService.getJobWantedInfos(keys,classId,workCity,workYear,ispush,curr,limit);
    }


    @RequestMapping("/getChooseCount")
    @ResponseBody
    public Integer getChooseCount(String keys,Integer classId,Integer workCity,Integer workYear,Integer ispush){
        if(classId!=null&&classId==-1){
            classId=null;
        }
        if(workYear!=null&&workYear==-1){
            workYear=null;
        }
        if(workCity!=null&&workCity==-1){
            workCity=null;
        }
        if(ispush!=null&&ispush==-1){
            ispush=null;
        }
        if(keys!=null&&keys.equals("")){
            keys=null;
        }
        return salesManagementService.getChooseCount(classId,workYear,workCity,ispush,keys);
    }


    /**
     * 推送处理
     * @return
     */
    @RequestMapping("push")
    @ResponseBody
    public JsonResult push(String userIds,String companyUserId,String classId,String filterIds){
        String[] ids = userIds.split(",");
        String[] fIds = filterIds.split(",");
        return salesManagementService.toDoPush(companyUserId,ids,fIds,classId);
    }

    /**
     *
     * @return
     */
    @RequestMapping("pushResult")
    public String pushResult(Integer successCount,Integer failCount,Integer sumCount,Map<String,Object> map,String companyName){
        map.put("successCount",successCount);
        map.put("failCount",failCount);
        map.put("sumCount",sumCount);
        map.put("companyName",companyName);
        return "/salesManagementent/pushResult";
    }


}
