package com.service;

import java.util.List;

import com.beans.Action;
import com.beans.User;
import com.daos.ActionDao;

public class ActionService {

    private static ActionDao actionDao =new ActionDao();

    //用户注册动作
    public static int addAction(Action action){
        return actionDao.addAction(action);
    }


    //用户修改动作
    public static boolean editAction(Action action){
        return actionDao.editAction(action);
    }

    //用户删除动作
    public static boolean delAction(Action action){
        return actionDao.delAction(action);
    }

    //得到用户所有动作列表
    public static List<Action> getAllActions(User user){
        return actionDao.getAllActions(user);
    }

    public static List<Action> getAllActions(User loginUser, int actionType) {
		// TODO 自动生成的方法存根
		return actionDao.getAllActions(loginUser,actionType);
	}

	public static boolean checkActionExist(Action action) {
		
		return actionDao.checkActionExist(action);
	}


    public static Action getAction(Integer action_id) {
        return actionDao.getAction(action_id);
    }
}
