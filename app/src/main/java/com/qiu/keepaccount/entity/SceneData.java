package com.qiu.keepaccount.entity;

import java.io.Serializable;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/1 15:13
 * @Description 场景
 */
public class SceneData implements Serializable {
    private String title;//标题
    private String desc;//描述
    private int resid;//图片资源id

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
