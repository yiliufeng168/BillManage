package com.biz;

import java.util.List;

import com.beans.Eventr;
import com.beans.User;
import com.service.EventService;

public class EventBiz {
	private static EventService eventService=new EventService();
	
	public static List<Eventr> getAllEvents(User user){
		return eventService.getAllEvents(user);
	}

	public static List<Eventr> getAllEvents(User loginUser, int eventInt) {
		// TODO 自动生成的方法存根
		return eventService.getAllEvents(loginUser,eventInt);
	}
	public static boolean checkEventExist(Eventr eventr ) {
		return eventService.checkEventExist(eventr);
	}
	public static int registEv(Eventr eventr) {
		return eventService.registEv(eventr);
	}
	public static void modifyEv(Eventr eventr) {
		eventService.modifyEv(eventr);
	}
	public static void delEv(Eventr eventr) {
		eventService.delEv(eventr);
	}
}
