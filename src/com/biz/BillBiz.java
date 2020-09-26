package com.biz;


import java.util.Date;
import java.util.List;

import com.beans.Bill;
import com.beans.User;
import com.service.BillService;

public class BillBiz {

	private static BillService billService=new BillService();
	public static void addBill(Bill bill) {
		billService.addBill(bill);
	}
	public static boolean checkEventIdExist(int event_id) {
		return billService.checkEventIdExist(event_id);
	}
	public static List<Bill> getAllBilList(User user, Integer event_type, Integer event_id, Date startDate,Date endDate,Float minMoney,Float maxMoney){
		return billService.getAllBilList(user, event_type,event_id, startDate, endDate, minMoney, maxMoney);
	}
	public static void delBill(Bill bill) {
		billService.delBill(bill);
	}
	public static void editBill(Bill bill) {
		billService.editBill(bill);
	}
}
