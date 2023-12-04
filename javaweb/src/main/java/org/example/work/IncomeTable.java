package org.example.work;

public class IncomeTable {
    private String request_id;
    private long  date;//时间戳，单位为秒
    private String  city;
    private String  type;
    private String  count;
    private String  income;

    public IncomeTable() {
    }

    public IncomeTable(String request_id, long date, String city, String type, String count, String income) {
        this.request_id = request_id;
        this.date = date;
        this.city = city;
        this.type = type;
        this.count = count;
        this.income = income;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
