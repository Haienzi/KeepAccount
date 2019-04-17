package com.qiu.keepaccount.entity;

import android.database.Cursor;

public class PiePointData {
    private int id;

    public PiePointData(Cursor cursor){
        this.id = cursor.getInt(0);
        this.amount = cursor.getDouble(1);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private double amount;

}
