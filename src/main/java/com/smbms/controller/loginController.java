package com.smbms.controller;

import com.smbms.pojo.User;
import com.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class loginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public String dologin(String userCode, String userPassword, Model model, HttpSession session){
        User user = userService.login(userCode, userPassword);
        if(user==null){
            model.addAttribute("error","用户名或密码错误");
            return "forward:/login.jsp";
        }
        session.setAttribute("loginUser",user);
        return "frame";
    }
    @GetMapping("/logout")
    public String doout(HttpSession session){
        session.removeAttribute("loginUser");
        return "redirect:/login.jsp";
    }
}
