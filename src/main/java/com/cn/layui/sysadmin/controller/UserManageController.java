package com.cn.layui.sysadmin.controller;

import com.cn.layui.sysadmin.service.UserManageService;
import com.cn.layui.sysadmin.utils.JsonResult;
import com.cn.layui.sysadmin.vo.CompanyUserInfoVo;
import com.cn.layui.sysadmin.vo.PersonalUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "UserManagement")
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    /**
     * 用户列表跳转
     * @param map
     * @param keys
     * @param sex
     * @param state
     * @param isCompany
     * @return
     */
    @RequestMapping(value = "UserList")
    public String index(Map<String,Object> map,String keys,Integer sex,Integer state,Integer isCompany){
        Integer count = getCountBySelect(keys, sex, state, isCompany);
        map.put("dataCount", count);
        map.put("keys", keys);
        map.put("sex", sex);
        map.put("state", state);
        map.put("isCompany", isCompany);
        return "/user/userList";
    }

    /**
     * 异常跳转页面
     * @return
     */
    public String error(){
        return "/user/error";
    }

    /**
     * 用户详情跳转
     * @param uid
     * @return
     */
    @RequestMapping(value = "UserDetails")
    public String getUserDetails(Integer uid,Integer isCompany,Map<String,Object> map ){
        map.put("uid", uid);
        if(isCompany==0){
            JsonResult personalUserInfo = getPersonalUserInfo(uid);
            if(personalUserInfo.getCode()==1){
                map.put("message",personalUserInfo.getMessage());
                return error();//无个人信息
            }
            PersonalUserInfoVo data = (PersonalUserInfoVo) personalUserInfo.getData();
            map.put("info",data);
            return "/user/userDetails";//跳到个人详情页面
        }else{
            JsonResult companyUserInfo = getCompanyUserInfo(uid);
            if(companyUserInfo.getCode()==1){
                map.put("message",companyUserInfo.getMessage());
                return error();//无企业信息
            }
            CompanyUserInfoVo data = (CompanyUserInfoVo) companyUserInfo.getData();
            map.put("info",data);
            return "/user/companyUserDetails";//跳到企业详情页面
        }
    }

    /**
     * 获取用户列表
     * @param keys
     * @param sex
     * @param state
     * @param isCompany
     * @param curr 当前页
     * @param limit 每页的数据条数
     * @return
     */
    @RequestMapping("getCommonUserList")
    @ResponseBody
//    @RequiresPermissions("sys:user:userList")
    public JsonResult getCommonUserList(String keys,Integer sex,Integer state,Integer isCompany,Integer curr,Integer limit) {
        if(sex!=null && sex==9){
            sex=null;
        }
        if(state!=null && state==9){
            state=null;
        }
        if(isCompany!=null && isCompany==9){
            isCompany=null;
        }
        if (curr ==null){
            curr=1;
        }
        if(limit==null){
            limit=15;
        }
        JsonResult commonUserList = userManageService.getCommonUserList(keys, sex, state, isCompany,curr,limit);
        System.out.println(commonUserList.toString());
        return commonUserList;
    }

    /**
     * 通过查询条件获取用户列表数据总条数
     * @param keys
     * @param sex
     * @param state
     * @param isCompany
     * @return
     */
    @RequestMapping(value = "getCount")
    @ResponseBody
    public Integer getCountBySelect(String keys,Integer sex,Integer state,Integer isCompany){
        if(sex!=null && sex==9){
            sex=null;
        }
        if(state!=null && state==9){
            state=null;
        }
        if(isCompany!=null && isCompany==9){
            isCompany=null;
        }
        return userManageService.selectCount(keys,sex,state,isCompany);
    }


    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    //@RequiresPermissions("sys:user:userInfo")
    public JsonResult getUserInfo(Integer userId) {
        Integer userId1 = userId;
        return userManageService.getUserInfo(userId);
    }


    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    @RequestMapping("/getPersonalUserInfo")
   // @RequiresPermissions("sys:user:userInfo")
    @ResponseBody
    public JsonResult getPersonalUserInfo(Integer userId) {
        return userManageService.getPersonalUserInfo(userId);
    }


    /**
     * 获取企业信息
     * @param userId
     * @return
     */
    @RequestMapping("/getCompanyUserInfo")
    @ResponseBody
    //@RequiresPermissions("sys:user:userInfo")
    public JsonResult getCompanyUserInfo(Integer userId) {
        return userManageService.getCompanyUserInfo(userId);
    }

}
