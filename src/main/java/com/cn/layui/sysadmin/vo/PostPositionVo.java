package com.cn.layui.sysadmin.vo;

public class PostPositionVo {
    private String workName;//招聘职位
    private String city;
    private Integer startMoney;
    private Integer endMoney;
    private String money;//薪资范围
    private Integer workYear;
    private Integer rank;
    private String companyName;
    private String phone;
    private String lastLoginTime;//上次登录时间
    private String releaseTime;//发布时间
    private Integer pushCount;//已推送人才
    private String rankString;//会员等级
    private String button;//按钮
    private Integer classId;//职位id
    private Integer companyId;//公司id
    private String workYearString;//工作年限
//0  经验不限      1  应届毕业生         2  一年以下\r\n       3  1-3年       4  3-5年
    public String getWorkYearString() {
        if(this.workYear!=null){
            if(workYear==0){
                return "经验不限";
            }
            if(workYear==1){
                return "应届毕业生";
            }
            if(workYear==2){
                return "一年以下";
            }
            if(workYear==3){
                return "1-3年";
            }
            if(workYear==4){
                return "3-5年";
            }

        }
        return workYearString;
    }

    public void setWorkYearString(String workYearString) {
        this.workYearString = workYearString;
    }

    public String getMoney() {
        String startMoney="面议";
        String endMoney="面议";
        if(this.startMoney!=0){
           startMoney=this.startMoney+"";
        }
        if(this.endMoney!=0){
           endMoney=this.endMoney+"";
        }
        return startMoney+" - "+endMoney;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    // 普通会员、银卡会员、金卡会员、铂金会员
    public String getRankString() {
        if(rank==2){
            return "金卡会员";
        }
        if(rank==1){
            return "银卡会员";
        }
        if(rank==3){
            return "铂金会员";
        }
        return "普通会员";
    }

    public void setRankString(String rankString) {
        this.rankString = rankString;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(Integer startMoney) {
        this.startMoney = startMoney;
    }

    public Integer getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(Integer endMoney) {
        this.endMoney = endMoney;
    }

    public Integer getWorkYear() {

        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }
}
