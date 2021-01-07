package com.service;

import java.util.Date;
import java.util.List;

import com.beans.Bill;
import com.beans.User;
import com.daos.BillDao;

public class BillService {
    private static BillDao billDao=new BillDao();

    //添加账单
    public static void addBill(Bill bill){
        billDao.addBill(bill);
    }

    //获取所有账单列表
    public static List<Bill> getAllBilList(User user, Integer action_type, Integer action_id, Date startDate,Date endDate,Float minMoney,Float maxMoney){
        List<Bill> bills=billDao.getAllBilList(user,action_type,action_id,startDate,endDate,minMoney,maxMoney);
        return bills;
    }

    //删除指定账单

    public static boolean delBill(Bill bill){
        return billDao.delBill(bill);
    }
    //检查账单是否存在
	public static boolean checkActionIdExist(int action_id) {
		// TODO 自动生成的方法存根
		return billDao.checkActionIdExist(action_id);
	}
	//修改账单
	public static void editBill(Bill bill) {
		billDao.editBill(bill);
	}
	
    
}