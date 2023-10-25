package org.example.service.impl;

import org.example.map.UserMapper;
import org.example.service.UserService;
import org.example.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> select() {
        return userMapper.select();
    }

    @Override
    public User  select_by_id(String user_id) {
        return userMapper.select_by_id(user_id);
    }
    @Override
    public User  user_load(String user_id,String password) {
        return userMapper.user_load(user_id,password);
    }

    @Override
    public int user_register(String user_id, String password) {
        return userMapper.user_register(user_id,password);
    }
    @Override
    public int user_update(String user_id, String password,String new_password, String phone_num,String description){
        return userMapper.user_update(user_id,password, new_password,phone_num,description);
    }
}
