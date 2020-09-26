package com.daos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.beans.Bill;
import com.beans.User;
import com.utils.DateConverUtils;
import com.utils.JdbcUtils;


public class BillDao {
    public void addBill(Bill bill) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="insert into bill (user_id,event_id,money,bill_date,note) values (?,?,?,?,?)";
        try{
            ps=conn.prepareStatement(sql);

            ps.setInt(1,bill.getUser_id());
            ps.setInt(2,bill.getEvent_id());
            ps.setFloat(3,bill.getMoney());
            ps.setString(4,DateConverUtils.convertDateToString(bill.getBill_date()));
            ps.setString(5,bill.getNote());
            int rs=ps.executeUpdate();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }
    }

    //event_type=2时，查找所有
    public List<Bill> getAllBilList(User user, Integer event_type, Integer event_id2, Date startDate, Date endDate, Float minMoney, Float maxMoney) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="SELECT * FROM bill as b,events as e where b.user_id=e.user_id and b.event_id=e.event_id and b.user_id=? ";
        List<Bill> bills =new ArrayList<Bill>();
        ResultSet rs=null;
        if(event_id2!=null) {
        	sql=sql+" and e.event_id="+event_id2;
        }
        if(event_type!=null){
            sql=sql+" and e.event_type="+event_type;
        }
        if(startDate!=null){
            sql=sql+" and b.bill_date>='"+DateConverUtils.convertDateToString(startDate)+"'";
        }
        if(endDate!=null){
            sql=sql+" and b.bill_date<='"+DateConverUtils.convertDateToString(endDate)+"'";
        }
        if(maxMoney!=null){
            sql=sql+" and b.money<="+maxMoney;
        }
        if(minMoney!=null){
            sql=sql+" and b.money>="+minMoney;
        }

        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,user.getUser_id());
            
            rs=ps.executeQuery();
            
            while (rs.next()){
                int bill_id=rs.getInt("b.bill_id");
                int user_id=rs.getInt("b.user_id");
                int event_id=rs.getInt("b.event_id");
                float money=rs.getFloat("b.money");
                Date bill_date=DateConverUtils.convertStringToDate(rs.getString("b.bill_date"));
                String note=rs.getString("b.note");
                Bill bill=new Bill(bill_id,user_id,event_id,money,bill_date,note);
                bills.add(bill);
            }
            return bills;
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(rs, ps, conn);
        }
        return null;

    }

    public boolean delBill(Bill bill) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="delete  from bill where bill_id=? and user_id=? ";   //防止非法操作，只有知道账单的编号，用户编号，事件编号才能删除
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bill.getBill_id());
            ps.setInt(2,bill.getUser_id());

            int rs=ps.executeUpdate();
            if(rs==1){
                return true;
            }

        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }
        return false;
    }

	public boolean checkEventIdExist(int event_id) {
		Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from bill where event_id=?";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,event_id);
            rs=ps.executeQuery();
            if(rs.next()){
                //已注册
                return true;
            }else {
                //未注册
                return false;
            }

        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(rs, ps, conn);
        }
        return false;
	}

	public void editBill(Bill bill) {
		Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="update bill set event_id=?, money=?, bill_date=? ,note=? where bill_id=? and user_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bill.getEvent_id());
            ps.setFloat(2,bill.getMoney());
            ps.setString(3,DateConverUtils.convertDateToString(bill.getBill_date()));
            ps.setString(4,bill.getNote());
            ps.setInt(5, bill.getBill_id());
            ps.setInt(6, bill.getUser_id());
            int rs=ps.executeUpdate();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }

	}

	
}
