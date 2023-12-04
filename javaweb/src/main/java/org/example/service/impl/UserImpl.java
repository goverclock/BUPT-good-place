package org.example.service.impl;

import org.example.map.UserMapper;
import org.example.oss.OSS;
import org.example.service.UserService;
import org.example.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    public int user_register(String user_id,String password, String user_name, String user_type, String name, String identity_type, String identity_id, String phone_num, String user_rank, String description, String register_city) {
        String register_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        return userMapper.user_register(user_id,password,user_name,user_type,name,identity_type,identity_id,phone_num,user_rank,description,register_city,register_time);
    }
    @Override
    public int user_update(String user_id,  String phone_num,String description){
        return userMapper.user_update(user_id,phone_num,description);
    }

    @Override
    public int information_update(String user_id, String user_name, String user_type, String name, String identity_type, String identity_id, String phone_num, String user_rank, String description, String register_city) {
        String register_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        return userMapper.information_update(user_id,user_name,user_type,name,identity_type,identity_id,phone_num,user_rank,description,register_city,register_time);
    }

    @Override
    public User get_user_information(String user_id) {
        return userMapper.get_user_information(user_id);
    }

    @Override
    public int update_password(String user_id, String password, String new_password) {
        return userMapper.update_password(user_id,password,new_password);
    }
}
