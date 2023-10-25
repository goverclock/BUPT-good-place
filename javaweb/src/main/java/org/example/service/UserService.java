package org.example.service;

import org.example.users.User;

import java.util.List;


public interface UserService {


    List<User> select();
    User select_by_id(String  user_id);

    User user_load(String user_id, String password);
    int user_register(String user_id,String password);
    int user_update(String user_id,String password,String new_password, String phone_num,String description);
}
