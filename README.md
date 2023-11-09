### 类：

User类:

```
public class User {
    private String user_id;
    private String user_name;//用户名
    private String password;
    private String user_type;
    private String name;//姓名
    private String identity_type;
    private String identity_id;
    private String phone_num;
    private String user_rank;
    private String description;
    private String register_city;
    private String register_time;
    private String modify_time;
    }
```

好去处类：

```
public class GoodPlace {
    private String request_id;
    private String user_id;
    private String city; //文档中没有提到，感觉需要用到
    private String type;
    private String topic_name;
    private String description;
    private List<String> files;
    private String max_price;
    private String end_time;
    private String create_time;
    private String change_time;
    private String state;//0表示已发布还未响应   1表示已响应还未接受  2表示已接受响应
}
```

欢迎来类：

```
public class Welcome {
    private String response_id;
    private String request_id;
    private String user_id;
    private String description;
    private List<String> files;
    private String create_time;
    private String change_time;
    private String state;//0表示已发布响应还未接受  1表示响应已被请求方接受
}
```

Result类(用于向前端返回数据)：//返回的结果都是一个Result对象

```
public class Result {
    private int code;//1代表成功，0代表失败
    private String msg;//详细的错误信息
    private Object data;//执行成功返回的数据信息  具体的返回类见具体接口
}
    
```

统计结果类：

```
public class Statistics {
    private String date;//YYYY-MM
    private long count;//成交数量
    private long agency_fee;
}
```



### 接口：

#### User接口：

##### 1.注册：(待定是否注册时直接完善用户信息) 已实现

 Path: “/register”

{ 

   String user_id;

   String password;

}

return:

{

   int code;

   String msg;

   User data;//返回用户信息

}

##### 2.登录： 已实现

Path: “/load”

{

   String user_id;

   String password;

}

return:

{

   int code;

   String msg;

   String token;

}

##### 3.修改信息：已实现

Path: “/update”

{ 

​	String user_id;

​	String password;

​	String new_password; 

​    String phone_num;

​    String description;

 }

return:

{

​	int code;

​    String msg;

​    User data;//返回修改后的用户信息

}

##### 4.完善个人信息：已实现

Path:"/information_update"//注册完成之后调用一次，确定用户信息

{

​	 String user_id;

	 String user_name;//用户名
	 String user_type;
	 String name;//姓名
	 String identity_type;
	 String identity_id;
	 String phone_num;
	 String user_rank;
	 String description;
	 String register_city;//具体到省市

}



#### 寻去处业务接口

##### 1.发布寻去处：已实现（表单提交）

Path: “/findplace”

{

​    String  user_id;

​    String  type;//地方类型

​    String  topic_name;//主题名称

​    String  description;

​	List<MultipartFile> files;上传的图片、视频或其他文件

​    String  city;

​    String  max_price;

​    String  end_time;

}

return:

{

   int code;

   String msg;

   GoodPlace data;//返回刚刚发布的寻去处信息

}

##### 2.查询发布的请求信息:(按类型、创建时间、修改时间、截止时间查询) 已实现

Path: "/query_request"

{

   String  user_id;

   String  type;

​	String topic_name;

   String  create_time;

   String  end_time;

}

return:

{

   int code;

   String msg;

   List<GoodPlace> data;//可能为空

}

##### 3.查询用户发布的请求信息的响应信息 已实现待完善（返回的只是文件路径）

Path: "/query_response_by_requestid"

{

​	String  request_id;

}

return:

{

​	 int code;

​     String msg;

​     List<Welcome>  data;

}

##### 4.用户修改已发布还没有响应的请求信息   已完成（表单提交）

Path: "/request_update"  

{

​    String  requset_id;

​    String  type;//地方类型

​    String  topic_name;//主题名称

​    String  description;

​	List<MultipartFile> files;上传的图片、视频或其他文件

​    String  city;

​    String  max_price;

​    String  end_time;

}

return:

{

   int code;

   String msg;

   GoodPlace  data;//修改成功返回数据信息，修改失败为null

}

##### 5.用户删除已发布还没有响应的请求信息   已完成

Path: "request_delete"

{

​    String  requset_id;

}

return:

{

​    int code;

​    String msg;

​    null;

}

##### 6.用户查询所有的请求信息  已实现待完善

Path: "query_request_by_user_id"

{

​    String  user_id;

}

return:

{

​    int code;

​    String msg;

​    LIst<GoodPlace>  data;

}

##### 7.用户接受欢迎来的响应   已实现

Path:"accept_response"  

{

 	String response_id;

}

return:

{

 	in code;

 	String msg;//成功或失败

 	data null;

}







#### 欢迎来业务接口：

##### 1.按地域查询所有的请求信息：已实现待完善（但是不是返回文件，返回文件路径）

Path: "/query_request_by_city"

{

​	String  city;

}

return:

{

​    int code;

​    String msg;

​    List<Goodplace>  data;

}

##### 2.提交欢迎来的响应信息：已实现（表单提交）

Path: "/response"

{

​	String request_id;

​    String user_id;//用户自身的id

​    String description;

​    List<MultipartFile>   files;//所有文本、图片和视频文件

}

return:

{

​    int code;

​    String msg;

​    Welcome  data;

}

##### 3.修改自己已提交但未接受的响应： 已完成（表单提交）

Path: "/response_update"

{

​	String response_id;

​    String description;

​    List<MultipartFile>   files;//所有文本、图片和视频文件

}

return:

{

​    int code;

​    String msg;

​    Welcome  data;

}

##### 4.删除自己已提交但未接受的响应：已完成

Path: "response_delete"

{

​	String response_id;

}

return:

{

​    int code;

​    String msg;

​    null;

}



#### 统计分析模块的接口：

##### 1.按起始年月、终止年月、某个地域不同类型请求查询达成中介费、笔数的明细

Path: "query_profit"

{

​	String start_time;

​    String end_time;

​    String city;

​    String type;

}

return:

{

​    int code;

​    String msg;

​    LIst<String>  data;(包含中介费和笔数  按顺序)

}

##### 2.按月份查询中介费、笔数的明细（默认显示近3个月的统计结果）

Path: "query_profit_by_month"

{

 	String  start_time;YYYY-MM

​     String  end_time;YYYY-MM

}

return:

{

​    int code;

​    String msg;

​    List<Statistics>  data;

}



