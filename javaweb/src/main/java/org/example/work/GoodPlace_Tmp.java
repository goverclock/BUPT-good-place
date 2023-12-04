package org.example.work;

import org.example.oss.OSS;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class GoodPlace_Tmp {
    private static String url_bath="https://goodplaces.oss-cn-beijing.aliyuncs.com/";
    private String request_id;
    private String user_id;
    private String city;
    private String type;
    private String topic_name;
    private String description;
    private ArrayList<String> files;
    private String max_price;
    private long end_time;
    private long create_time;
    private String change_time;
    private String state;

    public GoodPlace_Tmp(GoodPlace goodPlace ) throws Exception {
        this.request_id=goodPlace.getRequest_id();
        this.user_id=goodPlace.getUser_id();
        this.city=goodPlace.getCity();
        this.type=goodPlace.getType();
        this.topic_name=goodPlace.getTopic_name();
        this.description=goodPlace.getDescription();
        this.max_price=goodPlace.getMax_price();
        this.end_time=goodPlace.getEnd_time();
        this.create_time=goodPlace.getCreate_time();
        this.change_time=goodPlace.getChange_time();
        this.state=goodPlace.getState();
        files=new ArrayList<>();
        //待完善files初始化
        ArrayList<String> urls= OSS.list(goodPlace.getFile_dir());
        if(urls!=null) {
            for (String url : urls) {
                String tmp = url_bath + url;
                this.files.add(tmp);
            }
        }
        else {
            this.files=null;
        }

    }

    public static String getUrl_bath() {
        return url_bath;
    }

    public static void setUrl_bath(String url_bath) {
        GoodPlace_Tmp.url_bath = url_bath;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getChange_time() {
        return change_time;
    }

    public void setChange_time(String change_time) {
        this.change_time = change_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public GoodPlace_Tmp() {
    }
}
