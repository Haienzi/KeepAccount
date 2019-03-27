package com.qiu.keepaccount.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.Date;
import java.util.jar.Attributes;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:09
 * @Description
 */
public class Book extends RealmObject {

    @PrimaryKey
    private Integer id;

    private String bookName;

    private Scene scene;

    private Date createDate;
}
