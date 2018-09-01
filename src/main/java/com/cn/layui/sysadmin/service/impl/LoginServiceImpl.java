package com.cn.layui.sysadmin.service.impl;

import com.cn.layui.sysadmin.service.LoginService;
import org.springframework.stereotype.Service;

@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {
    @Override
    public boolean doLogin(String name, String password) {
        if(name.equals("admin") && password.equals("123456")){
            return true;
        }
        return false;
    }
}
