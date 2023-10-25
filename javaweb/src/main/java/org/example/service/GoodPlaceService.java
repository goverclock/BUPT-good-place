package org.example.service;

import org.example.work.GoodPlace;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GoodPlaceService {
    List<GoodPlace> query(String user_id,String type,String create_time,String change_time,String end_time);

    String find_goodplace(String user_id, String type, String topic_name, String description, List<MultipartFile> files, String city, String max_price, String end_time) throws IOException;

    GoodPlace query_by_request_id(String request_id);

    List<GoodPlace> query_bycity(String city);
}
