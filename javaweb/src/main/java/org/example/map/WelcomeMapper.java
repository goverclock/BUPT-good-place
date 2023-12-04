package org.example.map;

import org.apache.ibatis.annotations.*;
import org.example.work.Welcome;

import java.util.List;

@Mapper
public interface WelcomeMapper {
    @Select("select * from welcome where request_id=#{request_id}")
    List<Welcome> query_response_by_requestid( String request_id);

    @Select("select * from welcome where response_id=#{response_id}")
    Welcome query_by_response_id(String response_id);

    @Insert("insert into welcome (response_id, request_id, user_id, description, file_dir, create_time, change_time, state)"
            + "values('${response_id}','${request_id}','${user_id}','${description}','${file_dir}','${create_time}','${change_time}','${state}')")
    int add_response(String response_id, String request_id, String user_id, String description, String file_dir, long create_time, String change_time, String state);

    @Update("update welcome set description=#{description}, change_time=#{change_time} " +
            "where response_id=#{response_id} ")
    int response_update(String response_id, String description,String change_time);

    @Delete("delete  from welcome where response_id=#{response_id}")
    int response_delete(String response_id);

    @Update("update welcome set state=#{state} where response_id=#{response_id}")
    int update_state(String response_id,String state);
}
