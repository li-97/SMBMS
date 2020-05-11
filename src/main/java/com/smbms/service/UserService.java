package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.User;

import java.util.List;


public interface UserService {

    User getUserById(Long id)throws Exception;
    User login(String userCode,String userPassword);
    PageInfo<User> findUsers(int pageNum,int pageSize,String queryname,String queryUserRole);
    List<User>  findUserByCode(String userCode);
    void addUser(User user);
    void modifyUser(User user);
    int delUser(Long id);
    void modpwd(User user);
}
