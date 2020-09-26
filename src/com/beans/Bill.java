package com.beans;

import java.util.Date;

public class Bill {
    private Integer bill_id;    //账单id
    private Integer user_id;    //用户id
    private Integer event_id;        //事件id
    private Float money;        //操作金额
    private Date bill_date;     //操作时间
    private String note;        //备注
    public Bill() {
        super();
    }

    public Bill(Integer bill_id, Integer user_id, Integer event_id, Float money, Date bill_date, String note) {
        this.bill_id = bill_id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.money = money;
        this.bill_date = bill_date;
        this.note = note;
    }

    

	@Override
    public String toString() {
        return "Bill{" +
                "bill_id=" + bill_id +
                ", user_id=" + user_id +
                ", event_id=" + event_id +
                ", money=" + money +
                ", bill_date=" + bill_date +
                ", note='" + note + '\'' +
                '}';
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public void setBill_id(Integer bill_id) {
        this.bill_id = bill_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }



    public void setMoney(Float money) {
        this.money = money;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }

    public Integer getBill_id() {
        return bill_id;
    }

    public Integer getUser_id() {
        return user_id;
    }



    public Float getMoney() {
        return money;
    }

    public Date getBill_date() {
        return bill_date;
    }
}
