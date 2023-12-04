package org.example.work;

public class Statistics {
    private String date;//YYYY-MM
    private long count;//成交数量
    private long agency_fee;

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

    public Statistics(String date, long count, long agency_fee) {
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getAgency_fee() {
        return agency_fee;
    }

    public void setAgency_fee(long agency_fee) {
        this.agency_fee = agency_fee;
    }
}
