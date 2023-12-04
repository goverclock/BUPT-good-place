package org.example.work;

import org.example.oss.OSS;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class Welcome_Tmp {
    private static String url_bath="https://goodplaces.oss-cn-beijing.aliyuncs.com/";
    private String response_id;
    private String request_id;
    private String user_id;
    private String description;
    private ArrayList<String> files;
    private long create_time;
    private String change_time;
    private String state;

    public Welcome_Tmp(Welcome welcome) throws Exception {
        this.response_id = welcome.getResponse_id();
        this.request_id = welcome.getRequest_id();
        this.user_id = welcome.getUser_id();
        this.description = welcome.getDescription();
        this.create_time = welcome.getCreate_time();
        this.change_time = welcome.getChange_time();
        this.state = welcome.getState();
        files=new ArrayList<>();
        ArrayList<String> urls= OSS.list(welcome.getFile_dir());
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
        Welcome_Tmp.url_bath = url_bath;
    }

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
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

    public Welcome_Tmp() {
    }
}
