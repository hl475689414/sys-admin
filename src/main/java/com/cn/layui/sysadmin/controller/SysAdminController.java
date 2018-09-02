package com.cn.layui.sysadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * thmeleaf模板路由
 */
@Controller
public class SysAdminController {
    @RequestMapping(value = "index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "4xx")
    public String error(){
        return "error/4xx";
    }

    @RequestMapping(value = {"login.html","/"})
    public String login(){
        return "login";
    }

    @RequestMapping(value = "main")
    public String main(){
        return "main";
    }

    @RequestMapping(value = "addUser")
    public String addUser(){
        return "addUser";
    }

    @RequestMapping(value = "test")
    public String test(){
        System.out.println("执行l");
        return "test/";
    }
}
