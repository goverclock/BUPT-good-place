package org.example.service.impl;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.example.map.WelcomeMapper;
import org.example.oss.OSS;
import org.example.service.WelcomeService;
import org.example.work.GoodPlace;
import org.example.work.Welcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WelcomeImpl implements WelcomeService {
    @Autowired
    private WelcomeMapper welcomeMapper;

    @Override
    public List<Welcome> query_response_by_requestid(String request_id) {
        return welcomeMapper.query_response_by_requestid( request_id);
    }

    @Override
    public String add_response(String request_id, String user_id, String description, List<MultipartFile> files) throws Exception {
        String response_id= UUID.randomUUID().toString();
        Welcome welcome= welcomeMapper.query_by_response_id(response_id);
        while(welcome!=null){
            request_id= UUID.randomUUID().toString();
            welcome= welcomeMapper.query_by_response_id(response_id);
        }
        String currentPath = System.getProperty("user.dir");
        File res_dir=new File(currentPath+"/resource");
        if(!res_dir.exists()){
            res_dir.mkdir();
        }
        File welcome_dir=new File(currentPath+"/resource/welcome_response");
        if(!welcome_dir.exists()){
            welcome_dir.mkdir();
        }

        String file_dir=currentPath+"/resource/welcome_response/"+response_id;
        //String file_dir=currentPath+"/src/main/resources/welcome_response/"+response_id;
        File file=new File(file_dir);
        file.mkdir();
        if(files!=null) {
            for (MultipartFile file1 : files) {//上传到阿里云
                file1.transferTo(new File(file_dir + "/" + file1.getOriginalFilename()));
                OSS.upload(file_dir+"/" + file1.getOriginalFilename(),response_id+"/"+file1.getOriginalFilename());
            }
            //删除本地文件
            File dir=new File(file_dir);
            File[] files1 = dir.listFiles();
            for(File file2:files1){
                file2.delete();
            }
            dir.delete();
        }
        long create_time= Instant.now().getEpochSecond();
        String change_time="";
        String state="0";

        int flag= welcomeMapper.add_response(response_id,request_id,user_id,description,response_id,create_time,change_time,state);
        if(flag>0){
            return response_id;
        }
        else return null;
    }

    @Override
    public Welcome query_by_response_id( String response_id) {
        return welcomeMapper.query_by_response_id(response_id);
    }

    @Override
    public int response_update(String response_id, String description, List<MultipartFile> files) throws Exception {
        Welcome welcome=welcomeMapper.query_by_response_id(response_id);
        File directory = new File(welcome.getFile_dir());
        String file_path=welcome.getFile_dir();
        //删除阿里云OSS数据
        ArrayList<String> urls=OSS.list(file_path);
        for(String url:urls){
            OSS.delete(url);
        }
        String currentPath = System.getProperty("user.dir");
        File res_dir=new File(currentPath+"/resource");
        if(!res_dir.exists()){
            res_dir.mkdir();
        }
        File welcome_dir=new File(currentPath+"/resource/welcome_response");
        if(!welcome_dir.exists()){
            welcome_dir.mkdir();
        }
        String file_dir=currentPath+"/resource/goodplace_request/"+response_id;
        //String file_dir=currentPath+"/src/main/resources/goodplace_request/"+request_id;
        File file=new File(file_dir);
        file.mkdir();

        if(files!=null) {
            for (MultipartFile file1 : files) {
                file1.transferTo(new File(file_dir + "/" + file1.getOriginalFilename()));
                OSS.upload(file_dir+"/" + file1.getOriginalFilename(),response_id+"/"+file1.getOriginalFilename());
            }
            //删除本地文件
            File dir=new File(file_dir);
            File[] files1 = dir.listFiles();
            for(File file2:files1){
                file2.delete();
            }
            dir.delete();
        }

        String change_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        return welcomeMapper.response_update(response_id,description,change_time);
    }

    @Override
    public int response_delete(String response_id) throws Exception {
        Welcome welcome=welcomeMapper.query_by_response_id(response_id);
        String file_path=welcome.getFile_dir();
        ArrayList<String> urls=OSS.list(file_path);
        System.out.println(urls);
        for(String url:urls){
            OSS.delete(url);
        }
        return welcomeMapper.response_delete(response_id);
    }

    @Override
    public int update_state(String response_id) {
        return welcomeMapper.update_state(response_id,"1");
    }
}
