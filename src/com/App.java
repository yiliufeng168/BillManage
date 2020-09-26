package com;

import com.beans.User;
import com.biz.UserBiz;
import com.service.BillService;
import com.service.EventService;
import com.service.UserService;
import com.ui.BillQueryUI;
import com.ui.Menu;
import com.ui.SetEnterUi;
import com.ui.Ui;

public class App {
	public static void main(String[] args) {
		UserService userService=new UserService();
        EventService eventService=new EventService();
        BillService billService=new BillService();
        Ui ui=new Ui();
        ui.CreateJFrame("个人记账系统"); 
//        Menu m=new Menu("主菜单",UserBiz.Login(new User(null, "ylf", "123", null, null, null)));
//        SetEnterUi setEnterUi=new SetEnterUi(null,UserBiz.Login(new User(null, "ylf", "123", null, null, null)),0);
//        BillQueryUI billQueryUI=new BillQueryUI(null, UserBiz.Login(new User(null, "ylf", "123", null, null, null)));
	 
	}
}
