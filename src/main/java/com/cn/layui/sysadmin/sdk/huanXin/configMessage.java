package com.cn.layui.sysadmin.sdk.huanXin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configMessage {

    @Value("${EASEMOB_API_SERVER_HOST}")
    public  String EASEMOB_API_SERVER_HOST;
    @Value("${EASEMOB_APPKEY}")
    public  String EASEMOB_APPKEY;
    @Value("${EASEMOB_ID}")
    public  String EASEMOB_ID;
    @Value("${EASEMOB_SECRET}")
    public  String EASEMOB_SECRET;
    @Value("${EASEMOB_HTTP_SCHEMA}")
    public  String EASEMOB_HTTP_SCHEMA;
    @Value("${EASEMOB_ROLE_APPADMIN}")
    public  String EASEMOB_ROLE_APPADMIN;

    @Bean
    public EasemobInfo CreateEasemobInfo(){
        return new  EasemobInfo(EASEMOB_API_SERVER_HOST,EASEMOB_APPKEY,EASEMOB_ID,EASEMOB_SECRET,EASEMOB_HTTP_SCHEMA,EASEMOB_ROLE_APPADMIN);
    }


    public String getEASEMOB_API_SERVER_HOST() {
        return EASEMOB_API_SERVER_HOST;
    }

    public void setEASEMOB_API_SERVER_HOST(String EASEMOB_API_SERVER_HOST) {
        this.EASEMOB_API_SERVER_HOST = EASEMOB_API_SERVER_HOST;
    }

    public String getEASEMOB_APPKEY() {
        return EASEMOB_APPKEY;
    }

    public void setEASEMOB_APPKEY(String EASEMOB_APPKEY) {
        this.EASEMOB_APPKEY = EASEMOB_APPKEY;
    }

    public String getEASEMOB_ID() {
        return EASEMOB_ID;
    }

    public void setEASEMOB_ID(String EASEMOB_ID) {
        this.EASEMOB_ID = EASEMOB_ID;
    }

    public String getEASEMOB_SECRET() {
        return EASEMOB_SECRET;
    }

    public void setEASEMOB_SECRET(String EASEMOB_SECRET) {
        this.EASEMOB_SECRET = EASEMOB_SECRET;
    }

    public String getEASEMOB_HTTP_SCHEMA() {
        return EASEMOB_HTTP_SCHEMA;
    }

    public void setEASEMOB_HTTP_SCHEMA(String EASEMOB_HTTP_SCHEMA) {
        this.EASEMOB_HTTP_SCHEMA = EASEMOB_HTTP_SCHEMA;
    }

    public String getEASEMOB_ROLE_APPADMIN() {
        return EASEMOB_ROLE_APPADMIN;
    }

    public void setEASEMOB_ROLE_APPADMIN(String EASEMOB_ROLE_APPADMIN) {
        this.EASEMOB_ROLE_APPADMIN = EASEMOB_ROLE_APPADMIN;
    }
}
