package com.beans;

public class User {
    private Integer user_id;	//用户id
    private String user_name;	//用户名
    private String user_password;	//密码
    private Integer user_age;		//用户年龄
    private String user_phone;		//用户手机号
    private String user_sex;		//用户性别

    public User() {
        super();
    }

    public User(Integer user_id, String user_name, String user_password, Integer user_age, String user_phone, String user_sex) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_age = user_age;
        this.user_phone = user_phone;
        this.user_sex = user_sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_age=" + user_age +
                ", user_phone='" + user_phone + '\'' +
                ", user_sex='" + user_sex + '\'' +
                '}';
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_age(Integer user_age) {
        this.user_age = user_age;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public Integer getUser_age() {
        return user_age;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_sex() {
        return user_sex;
    }
}
