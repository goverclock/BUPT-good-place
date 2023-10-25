package org.example.map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.work.GoodPlace;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface GoodPlaceMapper {
    @Insert("insert into goodplaces (request_id, user_id, city, type, topic_name, description, file_dir, max_price, end_time, create_time, change_time, state)"
            + "values('${request_id}','${user_id}','${city}','${type}','${topic_name}','${description}','${file_dir}','${max_price}','${end_time}','${create_time}','${change_time}','${state}')")
    int find_goodplace(String request_id,String user_id, String type, String topic_name, String description, String  file_dir,String city, String max_price, String end_time,String create_time,String change_time, String state);

    @Select("select * from goodplaces where request_id=#{request_id}")
    GoodPlace query_by_request_id(String request_id);

    @Select("select * from goodplaces where city=#{city}")
    List<GoodPlace> query_bycity(String city);
}
