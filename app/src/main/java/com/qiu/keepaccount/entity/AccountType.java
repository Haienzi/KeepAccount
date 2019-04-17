package com.qiu.keepaccount.entity;


import org.litepal.crud.LitePalSupport;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/27 14:09
 * @Description 记录分类
 */
public class AccountType extends LitePalSupport {

    private int id;

    private String name; // 分类名称

    private Integer type; // 1 支出  2 收入

    private int typeIcon; //分类图片

    public void setId(int id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(int typeIcon) {
        this.typeIcon = typeIcon;
    }
}
