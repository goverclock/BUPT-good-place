package org.example.service.impl;

import org.example.map.WelcomeMapper;
import org.example.service.WelcomeService;
import org.example.work.GoodPlace;
import org.example.work.Welcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class WelcomeImpl implements WelcomeService {
    @Autowired
    private WelcomeMapper welcomeMapper;

    @Override
    public List<Welcome> query_response_by_requestid(String user_id,String request_id) {
        return welcomeMapper.query_response_by_requestid(user_id, request_id);
    }

    @Override
    public String add_response(String request_id, String user_id, String description, List<MultipartFile> files) throws IOException {
        String response_id= UUID.randomUUID().toString();
        Welcome welcome= welcomeMapper.query_by_response_id(user_id,response_id);
        while(welcome!=null){
            request_id= UUID.randomUUID().toString();
            welcome= welcomeMapper.query_by_response_id(user_id,response_id);
        }

        String file_dir="D:/java_code/javaweb/src/main/resources/welcome_response/"+response_id;
        File file=new File(file_dir);
        file.mkdir();
        for(MultipartFile file1:files){
            file1.transferTo(new File(file_dir+"/"+file1.getOriginalFilename()));
        }
        String create_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        String change_time="";
        String state="0";

        int flag= welcomeMapper.add_response(response_id,request_id,user_id,description,file_dir,create_time,change_time,state);
        if(flag>0){
            return response_id;
        }
        else return null;
    }

    @Override
    public Welcome query_by_response_id(String user_id, String response_id) {
        return welcomeMapper.query_by_response_id(user_id,response_id);
    }
}
