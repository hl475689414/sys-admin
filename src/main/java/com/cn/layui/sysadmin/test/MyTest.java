package com.cn.layui.sysadmin.test;

import com.cn.layui.sysadmin.utils.MD5Util;

public class MyTest {
    public static void main(String[] args) {
        String s = MD5Util.MD5("123456");
        System.out.println(s);
    }
}
