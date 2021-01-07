package com.beans;

import java.util.Date;

public class Bill {
    private Integer id;    //账单id
    private Integer user_id;    //用户id
    private Integer action_id;        //动作id
    private Float money;        //操作金额
    private Date date;     //操作时间
    private String note;        //备注
    public Bill() {
        super();
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", action_id=" + action_id +
                ", money=" + money +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getAction_id() {
        return action_id;
    }

    public void setAction_id(Integer action_id) {
        this.action_id = action_id;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Bill(Integer id, Integer user_id, Integer action_id, Float money, Date date, String note) {
        this.id = id;
        this.user_id = user_id;
        this.action_id = action_id;
        this.money = money;
        this.date = date;
        this.note = note;
    }
}
