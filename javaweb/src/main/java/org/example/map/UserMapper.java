package org.example.map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users_information")
    List<User> select();

    @Insert("insert into users_information (user_id,user_name,password,user_type,name,identity_type,identity_id,phone_num,user_rank,description,register_city,register_time,modify_time)" +
            " values('${user_id}','','${password}','','','','','','','','','','')")
    int user_register(String user_id, String password);


    @Select("select * from users_information where user_id= #{user_id} and password= #{password}")
    User user_load(String user_id,String password);

    @Select("select * from users_information where user_id= #{user_id}")
    User select_by_id(String user_id);

    @Update("update users_information set password=#{new_password},phone_num=#{phone_num}, description=#{description} " +
            "where user_id=#{user_id} and password=#{password}")
    int user_update(String user_id, String password, String new_password, String phone_num, String description);


}
