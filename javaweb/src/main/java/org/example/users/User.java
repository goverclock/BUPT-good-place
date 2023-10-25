package org.example.users;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }

    public String getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getUser_rank() {
        return user_rank;
    }

    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegister_city() {
        return register_city;
    }

    public void setRegister_city(String register_city) {
        this.register_city = register_city;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public User() {
    }

    public User(String user_id, String user_name, String password, String user_type, String name, String identity_type, String identity_id, String phone_num, String user_rank, String description, String register_city, String register_time, String modify_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.user_type = user_type;
        this.name = name;
        this.identity_type = identity_type;
        this.identity_id = identity_id;
        this.phone_num = phone_num;
        this.user_rank = user_rank;
        this.description = description;
        this.register_city = register_city;
        this.register_time = register_time;
        this.modify_time = modify_time;
    }
}
