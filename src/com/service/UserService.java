package com.service;

import com.beans.User;
import com.daos.UserDao;
import com.utils.CoderUtils;

public class UserService {
    private static UserDao userDao=new UserDao();

    //注册
    public static void regist(User user){
        String password=user.getUser_password();
        byte[] bytes=password.getBytes();
        try{
            byte[] encrptMD5= CoderUtils.encryptMD5(bytes);
            password=new String(encrptMD5);
            user.setUser_password(password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        userDao.regist(user);
    }
    //登陆
    public static User login(User user){
        try {
            byte[] encryptMD5=CoderUtils.encryptMD5(user.getUser_password().getBytes());
            String password=new String(encryptMD5);
            user.setUser_password(password);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return userDao.login(user);
    }
    //修改个人信息
    public static int modifyInfo(User user){

        return userDao.modifyInfo(user);
    }

    //返回false则代表该用户名已存在,返回true则代表用户名不存在
    public static boolean checkUserName(String user_name){
        return userDao.checkUserName(user_name);
    }
	public static String modifyPassword(User user) {
		// TODO 自动生成的方法存根
		 try {
	            byte[] encryptMD5=CoderUtils.encryptMD5(user.getUser_password().getBytes());
	            String password=new String(encryptMD5);
	            user.setUser_password(password);
	        } catch (Exception e) {
	            // TODO 自动生成的 catch 块
	            e.printStackTrace();
	        }
		 userDao.modifyPassword(user);
		 return user.getUser_password();
	}

}

