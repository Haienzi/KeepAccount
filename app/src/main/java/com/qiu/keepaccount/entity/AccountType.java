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

    private int userId; //用户id

    private byte[] typeIcon; //分类图片

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public byte[] getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(byte[] typeIcon) {
        this.typeIcon = typeIcon;
    }
}
