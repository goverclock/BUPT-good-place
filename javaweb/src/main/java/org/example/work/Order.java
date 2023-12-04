package org.example.work;

public class Order {
    private long start_time;
    private long end_time;
    private String city;
    private String type;

    public Order() {
    }

    public Order(long start_time, long end_time, String city, String type) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.city = city;
        this.type = type;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
