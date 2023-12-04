package org.example.service.impl;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.example.map.GoodPlaceMapper;
import org.example.oss.OSS;
import org.example.service.GoodPlaceService;
import org.example.work.GoodPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GoodPlaceImpl implements GoodPlaceService {
    String url_bath="https://goodplaces.oss-cn-beijing.aliyuncs.com/";

    @Autowired
    private GoodPlaceMapper goodPlaceMapper;
    @Override
    public List<GoodPlace> query(String user_id, String type,String topic_name, long create_time, long end_time) {
        if(create_time>0 && end_time>0) {
            return goodPlaceMapper.query(user_id, type, topic_name, create_time, end_time);
        }
        else if(create_time>0 && end_time<=0){
            return goodPlaceMapper.query_except_end_time(user_id,type,topic_name,create_time);
        }
        else if(create_time<=0 && end_time>0){
            return goodPlaceMapper.query_except_create_time(user_id,type,topic_name,end_time);
        }
        else {
            return goodPlaceMapper.query_except_time(user_id,type,topic_name);
        }
    }

    @Override
    public String find_goodplace(String user_id, String type, String topic_name, String description,MultipartFile[] files, String city, String max_price, long end_time) throws Exception {
        String request_id= UUID.randomUUID().toString();
        GoodPlace goodPlace= goodPlaceMapper.query_by_request_id(request_id);
        while(goodPlace!=null){
             request_id= UUID.randomUUID().toString();
             goodPlace= goodPlaceMapper.query_by_request_id(request_id);
        }
        String currentPath = System.getProperty("user.dir");
        File res_dir=new File(currentPath+"/resource");
        if(!res_dir.exists()){
            res_dir.mkdir();
        }
        File goodplace_dir=new File(currentPath+"/resource/goodplace_request");
        if(!goodplace_dir.exists()){
            goodplace_dir.mkdir();
        }
        String file_dir=currentPath+"/resource/goodplace_request/"+request_id;
        //String file_dir=currentPath+"/src/main/resources/goodplace_request/"+request_id;
        File file=new File(file_dir);
        file.mkdir();
        if(files!=null) {
            for (MultipartFile file1 : files) {//上传到阿里云OSS
                file1.transferTo(new File(file_dir + "/" + file1.getOriginalFilename()));
                OSS.upload(file_dir+"/" + file1.getOriginalFilename(),request_id+"/"+file1.getOriginalFilename());
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


        int flag= goodPlaceMapper.find_goodplace(request_id,user_id,type,topic_name,description,request_id,city,max_price,end_time,create_time,change_time,state);
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
        List<GoodPlace> goodPlaces=goodPlaceMapper.query_bycity(city);
        ArrayList<GoodPlace> goodPlaceArrayList=new ArrayList<>();
        for (GoodPlace goodPlace:goodPlaces){
            if(goodPlace.getState().equals("2")||goodPlace.getState().equals("3")){
                continue;
            }
            else {
                goodPlaceArrayList.add(goodPlace);
            }
        }
        return goodPlaceArrayList;
    }

    @Override
    public int request_update(String request_id, String type, String topic_name, String description, List<MultipartFile> files, String city, String max_price, long end_time) throws Exception {
        GoodPlace goodPlace=goodPlaceMapper.query_by_request_id(request_id);
        String file_path=goodPlace.getFile_dir();
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
        File goodplace_dir=new File(currentPath+"/resource/goodplace_request");
        if(!goodplace_dir.exists()){
            goodplace_dir.mkdir();
        }

        String file_dir=currentPath+"/resource/goodplace_request/"+request_id;
        //String file_dir=currentPath+"/src/main/resources/goodplace_request/"+request_id;
        File file=new File(file_dir);
        file.mkdir();
        if(files!=null) {
            for (MultipartFile file1 : files) {
                file1.transferTo(new File(file_dir + "/" + file1.getOriginalFilename()));
                OSS.upload(file_dir+"/" + file1.getOriginalFilename(),request_id+"/"+file1.getOriginalFilename());
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
        return goodPlaceMapper.request_update(request_id,type,topic_name,description,city,max_price,end_time,change_time);
    }

    @Override
    public List<GoodPlace> query_request_by_user_id(String user_id) {
        return goodPlaceMapper.query_request_by_user_id(user_id);
    }

    @Override
    public int request_delete(String request_id) throws Exception {
        GoodPlace goodPlace=goodPlaceMapper.query_by_request_id(request_id);
        String file_path=goodPlace.getFile_dir();
        ArrayList<String> urls=OSS.list(file_path);
        System.out.println(urls);
        for(String url:urls){
            OSS.delete(url);
        }
        return goodPlaceMapper.request_delete(request_id);
    }

    @Override
    public int update_state(String request_id,String state) {
        GoodPlace goodPlace=goodPlaceMapper.query_by_request_id(request_id);
        if(state.equals("1")) {
            if (goodPlace.getState().equals("0")) {
                goodPlaceMapper.update_state(request_id, "1");
            } else if (goodPlace.getState().equals("2")) {
                return 0;
            }

        }
        else if(state.equals("2")){
            if(goodPlace.getState().equals("2")){
                return 0;
            }
            else {
                goodPlaceMapper.update_state(request_id, "2");
            }

        }
        return 1;
    }

    @Override
    public void  end_time_check() {
        ArrayList<GoodPlace> goodPlaces= goodPlaceMapper.end_time_check();
        long cur_time=Instant.now().getEpochSecond();
        for(GoodPlace goodPlace:goodPlaces){
            if(goodPlace.getEnd_time()<cur_time && goodPlace.getState().equals("0")){
                goodPlaceMapper.update_state(goodPlace.getRequest_id(),"3");
            }
            else if(goodPlace.getEnd_time()>=cur_time && goodPlace.getState().equals("3")){
                goodPlaceMapper.update_state(goodPlace.getRequest_id(),"0");
            }
        }
    }
}
