package org.example.work;

public class Statistics {
    private String date;//YYYYMM
    private String count;//成交数量
    private String agency_fee;

    @Override
    public String toString() {
        return "Statistics{" +
                "date='" + date + '\'' +
                ", count='" + count + '\'' +
                ", agency_fee='" + agency_fee + '\'' +
                '}';
    }

    public Statistics() {
    }

    public Statistics(String date, String count, String agency_fee) {
        this.date = date;
        this.count = count;
        this.agency_fee = agency_fee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAgency_fee() {
        return agency_fee;
    }

    public void setAgency_fee(String agency_fee) {
        this.agency_fee = agency_fee;
    }
}
