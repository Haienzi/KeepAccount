package com.qiu.keepaccount.entity;

import java.io.Serializable;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/1 10:55
 * @Description
 */
public class Type implements Serializable {
    private String desc;//描述
    private int resid;//图片资源id

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }
}
