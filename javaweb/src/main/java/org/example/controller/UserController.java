package org.example.controller;

import org.example.result.Result;
import org.example.service.GoodPlaceService;
import org.example.service.UserService;
import org.example.service.WelcomeService;
import org.example.users.User;
import org.example.work.GoodPlace;
import org.example.work.Welcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GoodPlaceService goodPlaceService;
    @Autowired
    private WelcomeService welcomeService;


    //用户的操作
    @GetMapping("/test")
    public String test(){
        return "hello !!!";
    }
    @PostMapping("/load")
    public Result user_load(String user_id,String password){
        Result result=new Result();
        User user= userService.user_load(user_id,password);
        System.out.println(user);
        if(user !=null) {
            return result.success(user);
        }
        else {
            result.setMsg("账号或密码错误");
            return  result.failed();
        }
    }

    @PostMapping("/register")
    public Result user_register(String user_id,String password){
        Result result=new Result();
        User user= userService.select_by_id(user_id);
        System.out.println(user);
        if(user==null){
           userService.user_register(user_id,password);
           User new_user=userService.select_by_id(user_id);
           System.out.println(new_user);
            return result.success(new_user);
        }
        else{
            result.setMsg("用户名已存在");
            return result.failed();
        }

    }
    @PostMapping("/update")
    public Result user_update(String user_id,String password,String new_password, String phone_num,String description) {
        Result result = new Result();
        int flag = userService.user_update(user_id, password, new_password, phone_num, description);
        User new_user = userService.select_by_id(user_id);
        if (flag == 1) {
            return result.success(new_user);
        } else {
            result.setMsg("密码错误");
            return result.failed();
        }
    }
    //待完善
    @PostMapping("/query")
    public Result query(String user_id,String type,String create_time,String change_time, String end_time){
            Result result=new Result();
            List<GoodPlace> goodPlaces= goodPlaceService.query(user_id,type,create_time,change_time,end_time);

            result.success(goodPlaces);
            return result;
    }

    public Result query_by_request_id(String  request_id){
        Result result=new Result();
        GoodPlace goodPlaces= goodPlaceService.query_by_request_id(request_id);
        if(goodPlaces!=null) {
            result.success(goodPlaces);
        }
        else{
            result.setMsg("好去处请求丢失");
            result.failed();
        }
        return result;
    }
    //
    @PostMapping("/find")
    public Result find_goodplace(String user_id, String  type, String  topic_name, String  description, List<MultipartFile> files,String city,String  max_price, String  end_time) throws IOException {
           Result result=new Result();
           String request_id=goodPlaceService.find_goodplace(user_id,type,topic_name,description, files,city,max_price,end_time);
           if(request_id!=null){
               GoodPlace goodPlace= goodPlaceService.query_by_request_id(request_id);
               result.success(goodPlace);
           }
           else{
               result.setMsg("错误");
               result.failed();
           }
           return result;
    }
    //返回对应用户的请求的欢迎来响应
    @PostMapping("/query_response_by_requestid")
    public Result query_response_by_requestid(String user_id,String request_id){
           Result result=new Result();
           List<Welcome> welcomes= welcomeService.query_response_by_requestid(user_id,request_id);
           result.success(welcomes);
           return result;

    }


    //根据城市返回当地的好去处请求
    @PostMapping("/query_request_by_city")
    public Result query_request_by_city(String city){
        Result result=new Result();
        List<GoodPlace> goodPlaces=goodPlaceService.query_bycity(city);

        result.success(goodPlaces);
        return result;
    }
    //为好去处请求添加欢迎来响应
    @PostMapping("/response")
    private Result response(String request_id,String user_id,String description,List<MultipartFile> files) throws IOException {
        Result result=new Result();
        String response_id=welcomeService.add_response(request_id,user_id,description,files);
        if(response_id!=null){
            Welcome welcome=welcomeService.query_by_response_id(user_id,response_id);
            result.success(welcome);
        }
        else {
            result.setMsg("错误");
            result.failed();
        }

        return result;
    }

}
