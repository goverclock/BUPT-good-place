package org.example.service;

import org.example.users.User;

import java.util.List;


public interface UserService {


    List<User> select();
    User select_by_id(String  user_id);

    User user_load(String user_id, String password);
    int user_register(String user_id,String password, String user_name, String user_type, String name, String identity_type, String identity_id, String phone_num, String user_rank, String description, String register_city);
    int user_update(String user_id, String phone_num,String description);

    int information_update(String user_id, String user_name, String user_type, String name, String identityType, String identityId, String phoneNum, String userRank, String description, String registerCity);

    User get_user_information(String user_id);

    int update_password(String user_id, String password, String new_password);

    int verify(String user_id, String name,String identity_type, String identity_id);
}
