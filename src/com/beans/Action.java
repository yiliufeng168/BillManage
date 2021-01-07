package com.beans;

public class Action {
    private Integer id;       //动作id
    private Integer owner_id;        //用户id
    private String name;            //事件名
    private Integer type;        //动作类型 0支出 1收入

    public Action() {
        super();
    }

    public Action(Integer id, Integer owner_id, String name, Integer type) {
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
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
}

