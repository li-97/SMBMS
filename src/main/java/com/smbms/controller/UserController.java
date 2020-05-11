package com.smbms.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.smbms.exception.UserException;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import com.smbms.service.RoleService;
import com.smbms.service.UserService;

import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 * Created by Ray on 2020-04-30
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @RequestMapping("/list")
    public String usersDetail( String pageIndex,String queryname,String queryUserRole, Model model){
        int pageNo=1;
        if(pageIndex!=null){
            pageNo=Integer.parseInt(pageIndex);
        }

        PageInfo<User> pageInfo = userService.findUsers(pageNo, 5,queryname,queryUserRole);
        List<Role> roles = roleService.findRoles();
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("roleList",roles);
        return "userlist";
    }
    @RequestMapping(value = "checkcode")
        @ResponseBody
        public Map<String,String> checkcode(@RequestBody String userCode){

        List<User> user = userService.findUserByCode(userCode);
        Map<String,String> map=new HashMap<>() ;

            if (user.size()!=0){
                map.put("userCode","exist");
                return map;
            }else {
                map.put("userCode","ok");
                return map;
            }

    }
    @RequestMapping("/add")

    public String addUser(String userCode, String userName, String userPassword, String gender, @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday, String phone, String address, String userRole){
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        int i = Integer.parseInt(gender);
        user.setGender(i);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Long.parseLong(userRole));

        userService.addUser(user);
        return "userlist";
    }

    @RequestMapping(value = "/view/{method}/{id}")
    public String usermodView(@PathVariable("id") Long id,@PathVariable("method")String method, Model model) throws Exception {
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        if(method.equals("mod")){
            return "usermodify";
        }else {
            return "userview";
        }

    }

    @RequestMapping("/modify")
    public String modify(String uid,String userName,String gender,@DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,String phone,String address,String userRole){
       User user=new User();
       user.setId(Long.parseLong(uid));
       user.setUserName(userName);
       user.setGender(Integer.parseInt(gender));
       user.setBirthday(birthday);
       user.setPhone(phone);
       user.setAddress(address);
       user.setUserRole(Long.parseLong(userRole));
       userService.modifyUser(user);
        return "userlist";
    }
    @RequestMapping(value = "/del/{id}")
    @ResponseBody
    public Map<String, String> delUser(@PathVariable("id") String id){

        int i = userService.delUser(Long.parseLong(id));
        Map<String,String> map=new HashMap<>();

        if(i>0){
            map.put("delResult","true");
        }else {
            map.put("delResult","false");
        }
        return map;

    }
    @RequestMapping("/checkpwd")
    @ResponseBody
    public Map<String,String> checkpwd(@RequestBody String oldpassword,HttpSession session) throws Exception {
        User user = (User) session.getAttribute("loginUser");
        String password = userService.getUserById(user.getId()).getUserPassword();
        String opassword= JSONObject.parseObject(oldpassword).getString("oldpassword");
        Map<String,String> map=new HashMap<>();
        if(opassword.equals(password)){
            map.put("result","ture");
        }else if(!opassword.equals(password)){
        map.put("result","false");
        }else if(user==null){
            map.put("result","sessionerror");
        }else if (oldpassword==null||oldpassword.equals("")){
            map.put("result","error");
        }
        return map;

    }
    @RequestMapping("/modpwd")
    public String modpwd(String newpassword,HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        user.setUserPassword(newpassword);
        userService.modpwd(user);
        return "redirect:/login.jsp";
    }
}
