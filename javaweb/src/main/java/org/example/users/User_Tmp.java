package org.example.users;

public class User_Tmp {
        String user_id;
        String password;
        String new_password;
        String phone_num;
        String description;

    public String getUser_id() {
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public String getNew_password() {
        return new_password;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public String getDescription() {
        return description;
    }

    public User_Tmp() {
    }

    public User_Tmp(String user_id, String password, String new_password, String phone_num, String description) {
        this.user_id = user_id;
        this.password = password;
        this.new_password = new_password;
        this.phone_num = phone_num;
        this.description = description;
    }

    public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setNew_password(String new_password) {
            this.new_password = new_password;
        }

        public void setPhone_num(String phone_num) {
            this.phone_num = phone_num;
        }

        public void setDescription(String description) {
            this.description = description;
        }

}
