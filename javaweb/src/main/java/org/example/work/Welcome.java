package org.example.work;

public class Welcome {
    private String response_id;
    private String request_id;
    private String user_id;
    private String description;
    private String file_dir;
    private long create_time;
    private String change_time;
    private String state;

    @Override
    public String toString() {
        return "Welcome{" +
                "response_id='" + response_id + '\'' +
                ", request_id='" + request_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", description='" + description + '\'' +
                ", file_dir='" + file_dir + '\'' +
                ", create_time='" + create_time + '\'' +
                ", change_time='" + change_time + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public Welcome(String response_id, String request_id, String user_id, String description, String file_dir, long create_time, String change_time, String state) {
        this.response_id = response_id;
        this.request_id = request_id;
        this.user_id = user_id;
        this.description = description;
        this.file_dir = file_dir;
        this.create_time = create_time;
        this.change_time = change_time;
        this.state = state;
    }

    public Welcome() {
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

    public String getFile_dir() {
        return file_dir;
    }

    public void setFile_dir(String file_dir) {
        this.file_dir = file_dir;
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

    public void update_state(String response_id) {
    }
}
