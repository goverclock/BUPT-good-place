package org.example.map;

import org.apache.ibatis.annotations.*;
import org.example.work.GoodPlace;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface GoodPlaceMapper {
    @Select("select * from goodplaces")
    ArrayList<GoodPlace> end_time_check();

    @Insert("insert into goodplaces (request_id, user_id, city, type, topic_name, description, file_dir, max_price, end_time, create_time, change_time, state)"
            + "values('${request_id}','${user_id}','${city}','${type}','${topic_name}','${description}','${file_dir}','${max_price}','${end_time}','${create_time}','${change_time}','${state}')")
    int find_goodplace(String request_id,String user_id, String type, String topic_name, String description, String  file_dir,String city, String max_price, long end_time,long create_time,String change_time, String state);

    @Select("select * from goodplaces where request_id=#{request_id}")
    GoodPlace query_by_request_id(String request_id);

    @Select("select * from goodplaces where city=#{city}")
    List<GoodPlace> query_bycity(String city);

     @Select("select * from goodplaces " +
            "where user_id=#{user_id} and type like concat_ws('%',#{type},'%') and topic_name like concat_ws('%',#{topic_name},'%') " +
             "and create_time>=#{create_time} and end_time<=#{end_time}")
    List<GoodPlace> query(String user_id, String type, String topic_name, long create_time, long end_time);

    @Select("select * from goodplaces " +
            "where user_id=#{user_id} and type like concat_ws('%',#{type},'%') and topic_name like concat_ws('%',#{topic_name},'%') " +
            "and create_time>=#{create_time}")
    List<GoodPlace> query_except_end_time(String user_id, String type, String topic_name, long create_time);

    @Select("select * from goodplaces " +
            "where user_id=#{user_id} and type like concat_ws('%',#{type},'%') and topic_name like concat_ws('%',#{topic_name},'%') " +
            "and end_time<=#{end_time}")
    List<GoodPlace> query_except_create_time(String user_id, String type, String topic_name, long end_time);

    @Select("select * from goodplaces " +
            "where user_id=#{user_id} and type like concat_ws('%',#{type},'%') and topic_name like concat_ws('%',#{topic_name},'%') ")
    List<GoodPlace> query_except_time(String user_id, String type, String topic_name);

    @Update("update goodplaces set type=#{type}, topic_name=#{topic_name}, description=#{description}, city=#{city}, max_price=#{max_price}, end_time=#{end_time}," +
            " change_time=#{change_time}  where request_id=#{request_id}")
    int request_update(String request_id, String type, String topic_name, String description, String city, String max_price, long end_time,String change_time);

    @Select("select * from goodplaces where user_id=#{user_id}")
    List<GoodPlace> query_request_by_user_id(String user_id);

    @Delete("delete from goodplaces where request_id=#{request_id}")
    int request_delete(String request_id);

    @Update("update goodplaces set state=#{state} where request_id=#{request_id}")
    int update_state(String request_id, String state);
}
