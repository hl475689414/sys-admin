package com.cn.layui.sysadmin.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

public class PushMessage implements Serializable {
    private String target_type;
    private String[] target;
    private Map<String,String> msg;
    private String from;
    private Map<String ,Object> ext;

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String,Object> ext) {
        this.ext = ext;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public String[] getTarget() {
        return target;
    }

    public void setTarget(String[] target) {
        this.target = target;
    }

    public Map<String, String> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, String> msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "target_type='" + target_type + '\'' +
                ", target=" + Arrays.toString(target) +
                ", msg=" + msg +
                ", from='" + from + '\'' +
                '}';
    }
}
