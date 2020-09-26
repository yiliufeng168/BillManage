package com.beans;

public class Eventr {
    private Integer event_id;       //事件id
    private Integer user_id;        //用户id
    private String event_name;            //事件名
    private Integer event_type;        //事件类型 0支出 1收入

    public Eventr() {
        super();
    }

    public Eventr(Integer event_id, Integer user_id, String event_name, Integer event_type) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.event_name = event_name;
        this.event_type = event_type;
    }

    @Override
    public String toString() {
        return event_name;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }



    public Integer getUser_id() {
        return user_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_type(Integer event_type) {
        this.event_type = event_type;
    }

    public Integer getEvent_type() {
        return event_type;
    }
}

