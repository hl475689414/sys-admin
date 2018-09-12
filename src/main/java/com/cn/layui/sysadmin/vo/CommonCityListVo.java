package com.cn.layui.sysadmin.vo;

import java.util.List;

/**
 * Created by 李怀鹏 on 2018/6/25.
 */
public class CommonCityListVo {
    private int value;
    private String label;
    private List<CommonCityChildrenVo> children;

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

    public List<CommonCityChildrenVo> getChildren() {
        return children;
    }

    public void setChildren(List<CommonCityChildrenVo> children) {
        this.children = children;
    }
}
