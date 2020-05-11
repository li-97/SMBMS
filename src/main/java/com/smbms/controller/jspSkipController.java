package com.smbms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class jspSkipController {
    @RequestMapping("/useradd")
    public String skip(){
        return "useradd";
    }
    @RequestMapping("/modpwd")
    public String skippwdmod(){
        return "pwdmodify";
    }
}
