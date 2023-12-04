package org.example.map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.work.IncomeTable;

import java.util.ArrayList;

@Mapper
public interface AdminMapper {

    @Insert("insert into incometable (request_id,date,city,type,count,income) " +
            "values('${request_id}','${date}','${city}','${type}','${count}','${income}')")
    void add_incometable(String request_id, long date, String city, String type, String count, String income);

    @Select("select * from incometable " +
            "where #{start_time}<=date and date<=#{end_time} and city like concat_ws('%',#{city},'%') and type like concat_ws('%',#{type},'%')")
    ArrayList<IncomeTable> query_profit(long start_time, long end_time, String city, String type);

    @Select("select * from incometable  " +
            "where #{start_time}<=date and date<=#{end_time}  order by date")
    ArrayList<IncomeTable> query_profit_by_month(long start_time, long end_time);
}
