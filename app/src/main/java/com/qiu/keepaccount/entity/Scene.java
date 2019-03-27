package com.qiu.keepaccount.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:09
 * @Description
 */
public class Scene extends RealmObject {
    @PrimaryKey
    private Integer id;

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
