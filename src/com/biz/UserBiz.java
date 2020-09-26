package com.biz;

import com.beans.User;
import com.service.UserService;

public class UserBiz {
    private static UserService userService=new UserService();
    public static User Login(User user) {
        User loginUser=userService.login(user);
        return loginUser;
    }
    public static boolean checkName(String user_name) {
    	return userService.checkUserName(user_name);
    }
    
    public static void regist(User user) {
    	userService.regist(user);
    }
    public static void modifyInfo(User user) {
    	userService.modifyInfo(user);
    }
    public static String modifyPassword(User user) {
    	return userService.modifyPassword(user);
    }
}
