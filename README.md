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
    private String file_dir;
    private String max_price;
    private String end_time;
    private String create_time;
    private String change_time;
    private String state;
}
```

欢迎来类：

```
public class Welcome {
    private String response_id;
    private String request_id;
    private String user_id;
    private String description;
    private String file_dir;
    private String create_time;
    private String change_time;
    private String state;
}
```

Result类(用于向前端返回数据)：//返回的结果都是一个Result对象

```
public class Result {
    private Integer code;//1代表成功，0代表失败
    private String msg;//详细的错误信息
    private Object data;//执行成功返回的数据信息
    }
    
```

统计结果类：

```
public class Statistics {
    private String date;//YYYYMM
    private String count;//成交数量
    private String agency_fee;
    }
```



### 接口：

#### User接口：

##### 1.注册：(待定是否注册时直接完善用户信息)

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

##### 2.登录：

Path: “/load”

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

##### 3.修改信息：

Path: “/update”

{ 

  String user_id;

  String password;

  String new_password; 

  String phone_num;

  String description;

 }

return:

{

   int code;

   String msg;

   User data;//返回修改后的用户信息

}

#### 寻去处业务接口

##### 1.发布寻去处：

Path: “/findplace”

{

​    String  user_id;

​    String  type;

​    String  topic_name;

​    String  description;

​    File  file;(图片、文本或视频文件);

​    String  max_price;

​    String  end_time;

}

return:

{

   int code;

   String msg;

   GoodPlace data;

}

##### 2.查询发布的请求信息:(按类型、创建时间、修改时间、截止时间查询)

Path: "/query"

{

   String  user_id;

   String  type;

   String  create_time;

   String  change_time;

   String  end_time;

}

return:

{

   int code;

   String msg;

   GoodPlace[] data;//可能为空

}

##### 3.查询用户发布的请求信息的响应信息

Path: ""

{

   String  user_id;

   String  request_id;

}

return:

{

   int code;

   String msg;

   Welcome[]  data;

}

##### 4.用户修改已发布还没有响应的请求信息

Path: ""

{

   String  user_id;

   String  requset_id;

}

return:

{

   int code;

   String msg;

   GoodPlace  data;

}

##### 5.用户删除已发布还没有响应的请求信息

Path: ""

{

   String  user_id;

   String  requset_id;

}

return:

{

   int code;

   String msg;

   null;

}

##### 6.用户查询所有的请求信息

Path: ""

{

   String  user_id;

}

return:

{

   int code;

   String msg;

   Welcome[]  data;

}





#### 欢迎来业务接口：

##### 1.按地域查询所有的请求信息：

Path: ""

{

   String  city;

}

return:

{

   int code;

   String msg;

   Goodplace[]  data;

}

##### 2.提交欢迎来的响应信息：

Path: ""

{

   String response_id;

   String request_id;

   String user_id;//用户自身的id

   String description;

   File[]   file;//所有文本、图片和视频文件

}

return:

{

   int code;

   String msg;

   Welcome  data;

}

##### 3.修改自己已提交但未接受的响应：

Path: ""

{

   String response_id;

   String user_id;//用户自身的id

   String description;

   File[]   file;//所有文本、图片和视频文件

}

return:

{

   int code;

   String msg;

   Welcome  data;

}

##### 4.删除自己已提交但未接受的响应：

Path: ""

{

   String response_id;

   String user_id;//用户自身的id

}

return:

{

   int code;

   String msg;

   null;

}



#### 统计分析模块的接口：

##### 1.按起始年月、终止年月、某个地域不同类型请求查询达成中介费、笔数的明细

Path: ""

{

   String create_time;

   String end_time;

   String city;

   String type;

}

return:

{

   int code;

   String msg;

   String[]  data;(包含中介费和笔数  按顺序)

}

##### 2.按月份查询中介费、笔数的明细（默认显示近3个月的统计结果）

Path: ""

{

​     int month;//最近的几个月,数字代表该月之前的几个月这段时间，包括该月份

}

return:

{

   int code;

   String msg;

   Statistics[]  data;

}



