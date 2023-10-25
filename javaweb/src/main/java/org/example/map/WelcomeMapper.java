package org.example.map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.work.Welcome;

import java.util.List;

@Mapper
public interface WelcomeMapper {
    @Select("select * from welcome where request_id=#{request_id} and user_id=#{user_id}")
    List<Welcome> query_response_by_requestid(String user_id, String request_id);

    @Select("select * from welcome where response_id=#{response_id} and user_id=#{user_id}")
    Welcome query_by_response_id(String user_id,String response_id);

    @Insert("insert into welcome (response_id, request_id, user_id, description, file_dir, create_time, change_time, state)"
            + "values('${response_id}','${request_id}','${user_id}','${description}','${file_dir}','${create_time}','${change_time}','${state}')")
    int add_response(String response_id, String request_id, String user_id, String description, String file_dir, String create_time, String change_time, String state);
}
