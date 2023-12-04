package org.example.controller;

import jdk.nashorn.internal.objects.annotations.Optimistic;
import org.apache.ibatis.annotations.Options;
import org.example.oss.OSS;
import org.example.result.Result;
import org.example.service.AdminService;
import org.example.service.GoodPlaceService;
import org.example.service.UserService;
import org.example.service.WelcomeService;
import org.example.units.webJwt;
import org.example.users.User;
import org.example.users.User_Tmp;
import org.example.work.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableScheduling
@CrossOrigin(origins = "*", allowedHeaders = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GoodPlaceService goodPlaceService;
    @Autowired
    private WelcomeService welcomeService;
    @Autowired
    private AdminService adminService;

    @Autowired
    private webJwt webjwt;


    //用户的操作
    @GetMapping("/test")
    public String test() {
        return "hello !!!";
    }

    @PostMapping("/load")
    @CrossOrigin(origins = "*")
    public Result user_load( @RequestBody User user) {
        Result result = new Result();
        User user1 = userService.user_load(user.getUser_id(), user.getPassword());
        if (user1 != null) {
            Map<String,Object> claims=new HashMap<>();
            claims.put("user_id",user1.getUser_id());
            String jwt=webjwt.makejwt(claims);
            return result.success(jwt);
        } else {
            result.setMsg("账号或密码错误");
            return result.failed();
        }
    }
    @PostMapping("/update_password")
    @CrossOrigin(origins = "*")
    public Result update_password(@RequestBody User_Tmp user){
        Result result=new Result();
        User user1=userService.select_by_id(user.getUser_id());
        if(user1.getPassword().equals(user.getPassword())){
            int flag=userService.update_password(user.getUser_id(),user.getPassword(),user.getNew_password());
            if(flag>0){
                result.success(null);
            }
            else {
                result.setMsg("修改失败");
                result.failed();
            }
        }
        else{
            result.setMsg("密码错误");
            result.failed();
        }
        return result;
    }

    @PostMapping("/get_user_information")
    @CrossOrigin(origins = "*")
    public Result get_user_information(@RequestBody User user){
        Result result=new Result();
        User user1=userService.select_by_id(user.getUser_id());
        user1.setPassword("*****");
        result.success(user1);
        return result;
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public Result user_register(@RequestBody User user) {
        Result result = new Result();
        User user1 = userService.select_by_id(user.getUser_id());
        System.out.println(user);
        if (user1 == null) {
            userService.user_register(user.getUser_id(), user.getPassword(),user.getUser_name(),user.getUser_type(),user.getName(),user.getIdentity_type(),user.getIdentity_id(),user.getPhone_num(),user.getUser_rank(),user.getDescription(),user.getRegister_city());
            User new_user = userService.select_by_id(user.getUser_id());
            System.out.println(new_user);
            new_user.setPassword("*****");
            return result.success(new_user);
        } else {
            result.setMsg("用户名已存在");
            return result.failed();
        }
    }

    @PostMapping("/update_information")
    @CrossOrigin(origins = "*")
    public Result user_update( @RequestBody User_Tmp user) {
        Result result = new Result();
        int flag = userService.user_update(user.getUser_id(), user.getPhone_num(), user.getDescription());
        User new_user = userService.select_by_id(user.getUser_id());
        if (flag == 1) {
            new_user.setPassword("*****");
            return result.success(new_user);
        } else {
            result.setMsg("修改失败");
            return result.failed();
        }
    }

    @PostMapping("/information_update")
    @CrossOrigin(origins = "*")
    public Result information_update(@RequestBody User user ) {
        Result result = new Result();

        int flag = userService.information_update(user.getUser_id(), user.getUser_name(), user.getUser_type(), user.getName(), user.getIdentity_type(), user.getIdentity_id(), user.getPhone_num(), user.getUser_rank(), user.getDescription(), user.getRegister_city());
        if (flag > 0) {

            User user1 = userService.select_by_id(user.getUser_id());
            result.success(user1);
        } else {
            result.setMsg("信息修改失败");
            result.failed();
        }

        return result;
    }

    @PostMapping("/query_request")
    @CrossOrigin(origins = "*")
    public Result query(@RequestBody GoodPlace goodPlace ) throws Exception {
        Result result = new Result();
        state_update();
        List<GoodPlace> goodPlaces = goodPlaceService.query(goodPlace.getUser_id(), goodPlace.getType(), goodPlace.getTopic_name(), goodPlace.getCreate_time(), goodPlace.getEnd_time());
        ArrayList<GoodPlace_Tmp> goodPlaceTmps=new ArrayList<>();
        for(GoodPlace goodPlace1:goodPlaces){
            GoodPlace_Tmp tmp=new GoodPlace_Tmp(goodPlace1);
            goodPlaceTmps.add(tmp);
        }
        result.success(goodPlaceTmps);
        return result;
    }

    public Result query_by_request_id(String request_id) throws Exception {
        Result result = new Result();
        state_update();
        GoodPlace goodPlaces = goodPlaceService.query_by_request_id(request_id);
        if (goodPlaces != null) {
            result.success(new GoodPlace_Tmp(goodPlaces));
        } else {
            result.setMsg("好去处请求丢失");
            result.failed();
        }
        return result;
    }

    //
    @PostMapping("/findplace")
    @CrossOrigin(origins = "*")
    public Result find_goodplace(String user_id, String type, String topic_name, String description, MultipartFile[] files, String city, String max_price, long end_time) throws Exception {
        Result result = new Result();
        if(user_id==null){
            result.setMsg("用户不存在");
            return result.failed();
        }
        System.out.println("--------"+files.length);
        for(MultipartFile file:files){
            System.out.println(file);
        }
        String request_id = goodPlaceService.find_goodplace(user_id, type, topic_name, description, files, city, max_price, end_time);
        if (request_id != null) {
            GoodPlace goodPlace = goodPlaceService.query_by_request_id(request_id);
            result.success(new GoodPlace_Tmp(goodPlace));
        } else {
            result.setMsg("错误");
            result.failed();
        }
        return result;
    }

    //返回对应用户的请求的欢迎来响应
    @PostMapping("/query_response_by_requestid")
    @CrossOrigin(origins = "*")
    public Result query_response_by_requestid(@RequestBody GoodPlace goodPlace) throws Exception {
        Result result = new Result();
        List<Welcome> welcomes = welcomeService.query_response_by_requestid(goodPlace.getRequest_id());
        ArrayList<Welcome_Tmp> welcomeTmps=new ArrayList<>();
        for(Welcome welcome:welcomes){
            Welcome_Tmp tmp=new Welcome_Tmp(welcome);
            welcomeTmps.add(tmp);
        }
        result.success(welcomeTmps);
        return result;

    }

    //根据城市返回当地的好去处请求
    @PostMapping("/query_request_by_city")
    @CrossOrigin(origins = "*")
    public Result query_request_by_city(@RequestBody GoodPlace goodPlace ) throws Exception {
        Result result = new Result();
        state_update();
        List<GoodPlace> goodPlaces = goodPlaceService.query_bycity(goodPlace.getCity());
        ArrayList<GoodPlace_Tmp> goodPlaceTmps=new ArrayList<>();
        for(GoodPlace goodPlace1:goodPlaces){
            GoodPlace_Tmp tmp=new GoodPlace_Tmp(goodPlace1);
            goodPlaceTmps.add(tmp);
        }
        result.success(goodPlaceTmps);
        return result;
    }

    //为好去处请求添加欢迎来响应
    @PostMapping("/response")
    @CrossOrigin(origins = "*")
    private Result response(String request_id, String user_id, String description, List<MultipartFile> files) throws Exception {
        Result result = new Result();
        state_update();
        int flag=goodPlaceService.update_state(request_id,"1");
        if(flag==0){
            result.setMsg("该请求已被接受");
            result.failed();
            return result;
        }
        else {
            String response_id = welcomeService.add_response(request_id, user_id, description, files);
            if (response_id != null) {
                Welcome welcome = welcomeService.query_by_response_id(response_id);
                result.success(new Welcome_Tmp(welcome));
            } else {
                result.setMsg("响应添加时出错");
                result.failed();
            }
        }

        return result;
    }

    @PostMapping("/request_update")
    @CrossOrigin(origins = "*")
    private Result request_update(String request_id, String  type,String  topic_name,String  description,List<MultipartFile> files,String  city,String  max_price,long  end_time) throws Exception {
        Result result=new Result();
        GoodPlace goodPlace=goodPlaceService.query_by_request_id(request_id);
        if(goodPlace!=null && (goodPlace.getState().equals("0")||goodPlace.getState().equals("3"))){
            int flag=goodPlaceService.request_update(request_id,type,topic_name,description,files,city,max_price,end_time);
            if(flag>0){
                GoodPlace goodPlace1=goodPlaceService.query_by_request_id(request_id);
                result.success(new GoodPlace_Tmp(goodPlace1));
            }
            else{
                result.setMsg("修改失败");
                result.failed();
            }
        }
        else if(goodPlace.getState().equals("1")){
            result.setMsg("该请求已响应，不能修改");
            result.failed();
        }
        else if(goodPlace.getState().equals("2")){
            result.setMsg("该请求已接受，不能修改");
        }
        return  result;
    }

    @PostMapping("/response_update")
    @CrossOrigin(origins = "*")
    private Result response_update(String response_id,String description,List<MultipartFile>   files) throws Exception {
        Result result=new Result();
        Welcome welcome=welcomeService.query_by_response_id(response_id);
        if(welcome!=null && welcome.getState().equals("0")){
            int flag=welcomeService.response_update(response_id,description,files);
            if(flag>0){
                Welcome welcome1=welcomeService.query_by_response_id(welcome.getResponse_id());
                result.success(new Welcome_Tmp(welcome1));
            }
            else{
                result.setMsg("修改失败");
                result.failed();
            }
        }
        else{
            result.setMsg("响应已接受不能修改");
            result.failed();
        }

        return  result;
    }
    @PostMapping("query_request_by_user_id")
    @CrossOrigin(origins = "*")
    private Result query_request_by_user_id(@RequestBody User user) throws Exception {
        Result result=new Result();
        state_update();
        List<GoodPlace> goodPlaces=goodPlaceService.query_request_by_user_id(user.getUser_id());
        ArrayList<GoodPlace_Tmp> goodPlaceTmps=new ArrayList<>();
        for(GoodPlace goodPlace1:goodPlaces){
            GoodPlace_Tmp tmp=new GoodPlace_Tmp(goodPlace1);
            goodPlaceTmps.add(tmp);
        }
        result.success(goodPlaceTmps);
        return result;
    }
    @PostMapping("request_delete")
    @CrossOrigin(origins = "*")
    private Result request_delete(@RequestBody GoodPlace goodPlace) throws Exception {
        Result result=new Result();
        GoodPlace goodPlace1=goodPlaceService.query_by_request_id(goodPlace.getRequest_id());
        if(goodPlace1!=null) {
            if (goodPlace1.getState().equals("0")||goodPlace1.getState().equals("3")) {
                int flag = goodPlaceService.request_delete(goodPlace.getRequest_id());
                result.success("成功删除");
            } else if (goodPlace1.getState().equals("1")) {
                result.setMsg("请求已被响应无法删除");
                result.failed();
            } else if(goodPlace1.getState().equals("2")){
                result.setMsg("请求已被接受无法删除");
                result.failed();
            }
        }
        else {
            result.setMsg("该请求不存在");
            result.failed();
        }

        return result;
    }

    @PostMapping("response_delete")
    @CrossOrigin(origins = "*")
    private Result response_delete(@RequestBody Welcome welcome) throws Exception {
        Result result=new Result();

       Welcome welcome1=welcomeService.query_by_response_id(welcome.getResponse_id());
       if(welcome1!=null) {
           if (welcome1.getState().equals("0")) {
               int flag = welcomeService.response_delete(welcome.getResponse_id());
               result.success("成功删除");
           } else {
               result.setMsg("响应已被接受无法删除");
               result.failed();
           }
       }
       else {
           result.setMsg("该响应不存在");
           result.failed();
       }
        return result;
    }
    @PostMapping("/accept_response")
    @CrossOrigin(origins = "*")
    private Result accept_response(@RequestBody Welcome welcome){
        Result result=new Result();
        Welcome welcome1=welcomeService.query_by_response_id(welcome.getResponse_id());
        int flag= goodPlaceService.update_state(welcome1.getRequest_id(),"2");
        if(flag==0){
            result.failed();
            result.setMsg("该请求已完成");
            return result;
        }
        welcomeService.update_state(welcome.getResponse_id());
        GoodPlace goodPlace=goodPlaceService.query_by_request_id(welcome1.getRequest_id());
        long cur_time= Instant.now().getEpochSecond();
        IncomeTable income=new IncomeTable(goodPlace.getRequest_id(),cur_time,goodPlace.getCity(),goodPlace.getType(),"1","2");
        adminService.add_incometable(income);
        result.success("成功");
        return result;
    }

    @PostMapping("/query_profit")
    @CrossOrigin(origins = "*")
    private Result query_profit(@RequestBody Order order){
        Result result=new Result();
        ArrayList<String> data= adminService.query_profit(order);
        result.success(data);
        return  result;

    }
    @PostMapping("/query_profit_by_month")
    @CrossOrigin(origins = "*")
    private Result query_profit_by_month(@RequestBody Order order) throws ParseException {
        Result result=new Result();
        ArrayList<Statistics> statistics= adminService.query_profit_by_month(order.getStart_time(),order.getEnd_time());
        result.success(statistics);
        return result;
    }

    @Scheduled(cron = "0 0 * * * *")
    private void state_update(){//每小时检查一次请求是否已超过截止日期
        goodPlaceService.end_time_check();
        System.out.println("request状态检测");
    }

}


