package com.cn.layui.sysadmin.utils.easemob;

import com.cn.layui.sysadmin.entity.PhoneMessage;
import com.cn.layui.sysadmin.entity.PushMessage;
import com.cn.layui.sysadmin.entity.PushUser;
import com.cn.layui.sysadmin.sdk.huanXin.ClientSecretCredential;
import com.cn.layui.sysadmin.sdk.huanXin.EasemobInfo;
import com.cn.layui.sysadmin.sdk.huanXin.RequestHeaderValue;
import com.cn.layui.sysadmin.utils.EasemobClientUtils;
import com.cn.layui.sysadmin.utils.base.Base;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 环信推送--工具类
 */
@Component
public class pushUtil {
    private final static Logger logger= LoggerFactory.getLogger(pushUtil.class);
   @Autowired
    private EasemobInfo easemobInfo;

    /**
     * 将招聘意向人推给企业用户
     * @return
     */
    public  boolean pushUserIdToCompanyUserId(PushUser user, Integer companyUserId){
        String appkey = easemobInfo.getEASEMOB_APPKEY();
        String[] names = appkey.split("#");
        //推送电话号码相关信息
        PhoneMessage phoneMessage=new PhoneMessage();
        phoneMessage.setTarget_type("users");
        String[] targetUser = {user.getId() + "_1"};
        phoneMessage.setTarget(targetUser);
        Map<String, String> map = new HashMap<>();
        map.put("type", "txt");
        int startWorkYear = (int)(user.getStartWorkYear());
        Date date = new Date();
        String nowYear = new SimpleDateFormat("yyyy").format(date);
        Integer now = Integer.parseInt(nowYear);
        int workYear =now - startWorkYear;
        if(workYear==0){
            workYear=1;
        }
        map.put("msg", "我是"+user.getName()+",拥有"+workYear+"年工作经验，电话是"+user.getPhone());
        phoneMessage.setMsg(map);
        String requestPath = "https://a1.easemob.com/" + names[0] + "/" + names[1] + "/messages";
        phoneMessage.setFrom(user.getId() + "_0");
        JSONObject pushPhoneJsonObject = JSONObject.fromObject(phoneMessage);

        URL requestUrl = null;
        try {
            requestUrl = new URL(requestPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ClientSecretCredential csc = new ClientSecretCredential(easemobInfo.getEASEMOB_ID(), easemobInfo.getEASEMOB_SECRET(), easemobInfo.getEASEMOB_ROLE_APPADMIN());
        ObjectNode jn = EasemobClientUtils.sendHTTPRequest(requestUrl, csc, pushPhoneJsonObject.toString(), RequestHeaderValue.Method.POST);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Base.notEmpty(user.getPdfUrl())){
            PushMessage pushMessage = new PushMessage();
            pushMessage.setTarget_type("users");
            String[] target = {companyUserId + "_1"};
            pushMessage.setTarget(target);
            Map<String, String> msg = new HashMap<>();
            msg.put("type", "txt");
            msg.put("msg", user.getJobName()+"_"+user.getName()+"简历.pdf");
            pushMessage.setMsg(msg);
            Map<String,Object> extMap=new HashMap<>();
            extMap.put("type", 101);
            extMap.put("userId",user.getId());
            extMap.put("position", user.getJobName()+"_"+user.getName()+"简历.pdf");
            pushMessage.setExt(extMap);
            pushMessage.setFrom(user.getId() + "_0");
            JSONObject pushMessageJsonObject = JSONObject.fromObject(pushMessage);
            String path = "https://a1.easemob.com/" + names[0] + "/" + names[1] + "/messages";
            URL url = null;
            try {
                url = new URL(path);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            ClientSecretCredential credential = new ClientSecretCredential(easemobInfo.getEASEMOB_ID(),easemobInfo.getEASEMOB_SECRET(),easemobInfo.getEASEMOB_ROLE_APPADMIN());
            ObjectNode jsonNodes = EasemobClientUtils.sendHTTPRequest(url, credential, pushMessageJsonObject.toString(), RequestHeaderValue.Method.POST);
            logger.debug("环信推送请求结果：" + jsonNodes.get("data"));
            System.out.println("环信推送请求结果：" + jsonNodes.get("data"));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }




}
