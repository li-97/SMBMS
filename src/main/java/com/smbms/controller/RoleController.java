package com.smbms.controller;

import com.smbms.pojo.Role;
import com.smbms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @RequestMapping("/list")
    @ResponseBody
    public List<Role> getRoles() {
        List<Role> roles = roleService.findRoles();
        return roles;
    }
}
