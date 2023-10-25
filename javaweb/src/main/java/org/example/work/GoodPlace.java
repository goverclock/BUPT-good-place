package org.example.work;

public class GoodPlace {
    private String request_id;
    private String user_id;
    private String city;
    private String type;
    private String topic_name;
    private String description;
    private String file_dir;
    private String max_price;
    private String end_time;
    private String create_time;
    private String change_time;
    private String state;

    @Override
    public String toString() {
        return "GoodPlace{" +
                "request_id='" + request_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", place='" + city + '\'' +
                ", type='" + type + '\'' +
                ", topic_name='" + topic_name + '\'' +
                ", description='" + description + '\'' +
                ", file_dir='" + file_dir + '\'' +
                ", max_price='" + max_price + '\'' +
                ", end_time='" + end_time + '\'' +
                ", create_time='" + create_time + '\'' +
                ", change_time='" + change_time + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getPlace() {
        return city;
    }

    public void setPlace(String place) {
        this.city = place;
    }

    public GoodPlace() {
    }

    public GoodPlace(String request_id, String user_id, String type, String topic_name, String description, String file_dir, String max_price, String end_time, String create_time, String change_time, String state) {
        this.request_id = request_id;
        this.user_id = user_id;
        this.type = type;
        this.topic_name = topic_name;
        this.description = description;
        this.file_dir = file_dir;
        this.max_price = max_price;
        this.end_time = end_time;
        this.create_time = create_time;
        this.change_time = change_time;
        this.state = state;
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

    public String getFile_dir() {
        return file_dir;
    }

    public void setFile_dir(String file_dir) {
        this.file_dir = file_dir;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
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
}

