package org.example.service.impl;

import org.example.map.GoodPlaceMapper;
import org.example.service.GoodPlaceService;
import org.example.work.GoodPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class GoodPlaceImpl implements GoodPlaceService {

    @Autowired
    private GoodPlaceMapper goodPlaceMapper;
    @Override
    public List<GoodPlace> query(String user_id, String type, String create_time, String change_time, String end_time) {
        return null;
    }

    @Override
    public String find_goodplace(String user_id, String type, String topic_name, String description,List<MultipartFile> files, String city, String max_price, String end_time) throws IOException {
        String request_id= UUID.randomUUID().toString();
        GoodPlace goodPlace= goodPlaceMapper.query_by_request_id(request_id);
        while(goodPlace!=null){
             request_id= UUID.randomUUID().toString();
             goodPlace= goodPlaceMapper.query_by_request_id(request_id);
        }

        String file_dir="D:/java_code/javaweb/src/main/resources/goodplace_request/"+request_id;
        File file=new File(file_dir);
        file.mkdir();
        for(MultipartFile file1:files){
            file1.transferTo(new File(file_dir+"/"+file1.getOriginalFilename()));
        }
        String create_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        String change_time="";
        String state="0";

        int flag= goodPlaceMapper.find_goodplace(request_id,user_id,type,topic_name,description,file_dir,city,max_price,end_time,create_time,change_time,state);
        if(flag>0){
            return request_id;
        }
        else return null;
    }

    @Override
    public GoodPlace query_by_request_id(String request_id) {
        return goodPlaceMapper.query_by_request_id(request_id);
    }

    @Override
    public List<GoodPlace> query_bycity(String city) {
        return goodPlaceMapper.query_bycity(city);
    }
}
