package com.qiu.keepaccount.entity;

import org.litepal.crud.LitePalSupport;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:09
 * @Description
 */
public class Scene{

    private int id;

    private String sceneName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }
}
