package com.qiu.keepaccount.entity;

import android.database.Cursor;

public class LinearPointData {
    public String getTime() {
        /*String[] times = time.split("-");
        if(Integer.valueOf(times[1]) == 1 && Integer.valueOf(times[2]) == 1){
            return times[0]+"年";
        }else{
            return times[1]+"月"+times[2]+"日";
        }*/
        return  time;

    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    private String time;
    private Double money;

    public LinearPointData(Cursor cursor){
        this.time = cursor.getString(0);
        this.money = cursor.getDouble(1);
    }

}
