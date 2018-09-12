package com.cn.layui.sysadmin.vo;

import java.util.List;

/**
 * Created by 李怀鹏 on 2018/6/25.
 */
public class JobClassListVo {
    private int value;
    private String label;
    private List<JobClassChildrenVo> children;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<JobClassChildrenVo> getChildren() {
        return children;
    }

    public void setChildren(List<JobClassChildrenVo> children) {
        this.children = children;
    }
}
