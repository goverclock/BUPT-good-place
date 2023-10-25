package org.example.work;

public class FinishTable {
    private String  request_id;
    private String  publish_user_id;
    private String  response_user_id;
    private String  finish_time;
    private String  publish_price;
    private String  response_price;

    @Override
    public String toString() {
        return "FinishTable{" +
                "request_id='" + request_id + '\'' +
                ", publish_user_id='" + publish_user_id + '\'' +
                ", response_user_id='" + response_user_id + '\'' +
                ", finish_time='" + finish_time + '\'' +
                ", publish_price='" + publish_price + '\'' +
                ", response_price='" + response_price + '\'' +
                '}';
    }

    public FinishTable() {
    }

    public FinishTable(String request_id, String publish_user_id, String response_user_id, String finish_time, String publish_price, String response_price) {
        this.request_id = request_id;
        this.publish_user_id = publish_user_id;
        this.response_user_id = response_user_id;
        this.finish_time = finish_time;
        this.publish_price = publish_price;
        this.response_price = response_price;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getPublish_user_id() {
        return publish_user_id;
    }

    public void setPublish_user_id(String publish_user_id) {
        this.publish_user_id = publish_user_id;
    }

    public String getResponse_user_id() {
        return response_user_id;
    }

    public void setResponse_user_id(String response_user_id) {
        this.response_user_id = response_user_id;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getPublish_price() {
        return publish_price;
    }

    public void setPublish_price(String publish_price) {
        this.publish_price = publish_price;
    }

    public String getResponse_price() {
        return response_price;
    }

    public void setResponse_price(String response_price) {
        this.response_price = response_price;
    }
}
