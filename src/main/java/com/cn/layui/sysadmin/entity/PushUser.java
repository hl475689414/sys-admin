package com.cn.layui.sysadmin.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PushUser {
    private Integer id;
    private String name;
    private String phone;
    private Integer workYear;//工作年限
    private String jobName;//职位
    private String pdfUrl;//简历地址
    private Integer startWorkYear;//开始工作年份

    public Integer getStartWorkYear() {
        return startWorkYear;
    }

    public void setStartWorkYear(Integer startWorkYear) {
        this.startWorkYear = startWorkYear;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getWorkYear() {
        Date date = new Date();
        String nowYear = new SimpleDateFormat("yyyy").format(date);
        Integer now = Integer.parseInt(nowYear);
        int workYear =now - startWorkYear;
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
