package org.example.service;

import org.example.work.IncomeTable;
import org.example.work.Order;
import org.example.work.Statistics;

import java.text.ParseException;
import java.util.ArrayList;

public interface AdminService {
    void add_incometable(IncomeTable income);

    ArrayList<String> query_profit(Order order);

    Statistics query_profit_by_month(long start_time, long end_time) throws ParseException;
}
