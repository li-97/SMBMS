package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.RoleMapper;
import com.smbms.dao.UserMapper;
import com.smbms.pojo.User;
import com.smbms.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * description:
 * Created by Ray on 2020-04-30
 */
@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    @Transactional(readOnly = true)//事务管理
    public User getUserById(Long id) throws Exception {
        User user = userMapper.selectByPrimaryKey(id);
        user.setRole( roleMapper.selectByPrimaryKey(user.getUserRole()));
        return user;
    }

    @Override
    public User login(String userCode, String userPassword) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserPasswordEqualTo(userPassword);
        criteria.andUserCodeEqualTo(userCode);
        List<User> users = userMapper.selectByExample(example);
        if(users.size()<1){
            return  null;
        }
        return users.get(0);
    }

    @Override
    public PageInfo<User> findUsers(int pageNum,int pageSize,String queryname,String queryUserRole) {
        PageHelper.startPage(pageNum,pageSize);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (queryname!=null&&!"".equals(queryname)){
            criteria.andUserNameLike("%"+queryname+"%");
        }
        if (queryUserRole!=null&&!"".equals(queryUserRole)){
            long parseLong = Long.parseLong(queryUserRole);
            criteria.andUserRoleEqualTo(parseLong);
        }
        List<User> users = userMapper.selectByExample(example);
        for (User user:users
             ) {
            user.setRole(roleMapper.selectByPrimaryKey(user.getUserRole()));
            user.setAge(new Date().getYear()-user.getBirthday().getYear());
        }
         PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public List<User> findUserByCode(String userCode) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        List<User> users = userMapper.selectByExample(example);

        return  users;
    }

    @Override
    public void addUser(User user) {
        int insert = userMapper.insert(user);


    }

    @Override
    public void modifyUser(User user) {
        /*userMapper.updateByPrimaryKey(user);*/
userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int delUser(Long id) {
        int i = userMapper.deleteByPrimaryKey(id);
        return i;

    }

    @Override
    public void modpwd(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }


}
