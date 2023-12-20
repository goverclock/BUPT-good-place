package org.example.service.impl;

import org.example.map.AdminMapper;
import org.example.service.AdminService;
import org.example.work.IncomeTable;
import org.example.work.Order;
import org.example.work.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class AdminImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void add_incometable(IncomeTable income) {
        adminMapper.add_incometable(income.getRequest_id(),income.getDate(),income.getCity(),income.getType(),income.getCount(),income.getIncome());
    }

    @Override
    public ArrayList<String> query_profit(Order order) {
       ArrayList<IncomeTable> tables=adminMapper.query_profit(order.getStart_time(),order.getEnd_time(),order.getCity(),order.getType());
       long count=0;  long income=0;
       if(tables!=null) {
           for (IncomeTable table : tables) {
               count += Integer.parseInt(table.getCount());
               income += Integer.parseInt(table.getIncome());
           }
       }
       ArrayList<String> data=new ArrayList<>();
       data.add(Long.toString(count));
       data.add(Long.toString(income));
       return data;
    }

    @Override
    public Statistics query_profit_by_month(long start_time, long end_time)  {
        Statistics statistics=new Statistics();
        ArrayList<IncomeTable> tables= adminMapper.query_profit_by_month(start_time,end_time);
        if(tables!=null){
            long count=0;long income=0;
            for(IncomeTable table:tables){
                count += Integer.parseInt(table.getCount());
                income += Integer.parseInt(table.getIncome());
            }
            String date=new SimpleDateFormat("yyyy-MM-dd").format(start_time*1000);
            statistics.setDate(date);
            statistics.setCount(count);
            statistics.setAgency_fee(income);
        }
        else{
            String date=new SimpleDateFormat("yyyy-MM-dd").format(start_time*1000);
            statistics.setDate(date);
            statistics.setCount(0);
            statistics.setAgency_fee(0);
        }
//        System.out.println(statistics);

        return statistics;
    }
}
