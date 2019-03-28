package com.qiu.keepaccount.entity;

import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.jar.Attributes;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:09
 * @Description
 */
public class Book extends LitePalSupport {

    private int id;

    private String bookName;

    private Scene scene;

    private Date createDate;
}
