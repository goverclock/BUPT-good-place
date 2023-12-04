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
    public ArrayList<Statistics> query_profit_by_month(long start_time, long end_time) throws ParseException {
        ArrayList<Statistics> statistics=new ArrayList<>();
        ArrayList<IncomeTable> tables= adminMapper.query_profit_by_month(start_time,end_time);
        //把年月日转换为年月
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM");

        if(tables!=null){
            String time=outputFormat.format(tables.get(0).getDate()*1000);//将秒的时间戳转换为毫秒的时间戳
            long count=0;
            long income=0;
            for(IncomeTable table:tables){
                if(!outputFormat.format(table.getDate()*1000).equals(time)){
                    Statistics statistics_tmp=new Statistics(time,count,income);
                    statistics.add(statistics_tmp);
                    count=Integer.parseInt(table.getCount());
                    income=Integer.parseInt(table.getIncome());
                    time=outputFormat.format(table.getDate()*1000);
                }
                else {
                    count+=Integer.parseInt(table.getCount());
                    income+=Integer.parseInt(table.getIncome());
                }

            }
            Statistics statistics_tmp=new Statistics(time,count,income);
            statistics.add(statistics_tmp);
        }
        return statistics;
    }
}
