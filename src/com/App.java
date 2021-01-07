package com;



import com.beans.User;
import com.service.UserService;
import com.ui.LoginUi;
import com.ui.MenuUi;

public class App {
	public static void main(String[] args) {
        new InitSystem();

        LoginUi loginUi =new LoginUi();
//        loginUi.CreateJFrame("个人记账系统");
        MenuUi m=new MenuUi("主菜单", UserService.login(new User(null, "小公主", "123456", null, null, null)));
//        SetEnterUi setEnterUi=new SetEnterUi(null,UserBiz.Login(new User(null, "ylf", "123", null, null, null)),0);
//        BillQueryUI billQueryUI=new BillQueryUI(null, UserBiz.Login(new User(null, "小公主", "123456", null, null, null)));
	 
	}
}
