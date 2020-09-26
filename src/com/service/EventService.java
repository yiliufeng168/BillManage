package com.service;

import java.util.List;

import com.beans.Eventr;
import com.beans.User;
import com.daos.EventDao;

public class EventService {

    private EventDao eventDao=new EventDao();

    //用户注册事件
    public int registEv(Eventr eventr){
        return eventDao.registEv(eventr);
    }

    //得到用户所有事件列表
    public List<Eventr> getAllEvents(User user){
        return eventDao.getAllEvents(user);
    }

    //用户修改事件
    public boolean modifyEv(Eventr eventr){
        return eventDao.modifyEv(eventr);
    }

    //用户删除事件
    public boolean delEv(Eventr eventr){
        return eventDao.delEv(eventr);
    }

	public List<Eventr> getAllEvents(User loginUser, int eventInt) {
		// TODO 自动生成的方法存根
		return eventDao.getAllEvents(loginUser,eventInt);
	}

	public boolean checkEventExist(Eventr eventr) {
		
		return eventDao.checkEventExist(eventr);
	}


}
