package org.example.service;

import org.example.work.GoodPlace;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface GoodPlaceService {
    List<GoodPlace> query(String user_id,String type,String topic_name,long create_time,long end_time);

    String find_goodplace(String user_id, String type, String topic_name, String description, MultipartFile[] files, String city, String max_price, long end_time) throws Exception;

    GoodPlace query_by_request_id(String request_id);

    List<GoodPlace> query_bycity(String city);

    int request_update(String request_id, String type, String topic_name, String description, List<MultipartFile> files, String city, String max_price, long end_time) throws Exception;

    List<GoodPlace> query_request_by_user_id(String user_id);

    int request_delete(String request_id) throws Exception;

    int update_state(String request_id,String state);

   void end_time_check();
}
