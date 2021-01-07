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

    //添加账单
    public void addBill(Bill bill) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="insert into bills (user_id,action_id,money,date,note) values (?,?,?,?,?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bill.getUser_id());
            ps.setInt(2,bill.getAction_id());
            ps.setFloat(3,bill.getMoney());
            ps.setString(4,DateConverUtils.convertDateToString(bill.getDate()));
            ps.setString(5,bill.getNote());
            int rs=ps.executeUpdate();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }
    }

    //event_type=2时，查找所有账单
    public List<Bill> getAllBilList(User user, Integer action_type, Integer action_id, Date startDate, Date endDate, Float minMoney, Float maxMoney) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="SELECT * FROM bills as b,actions as a where b.user_id=a.owner_id and b.action_id=a.id and b.user_id=? ";
        List<Bill> bills =new ArrayList<Bill>();
        ResultSet rs=null;
        if(action_id!=null) {
        	sql=sql+" and a.id="+action_id;
        }
        if(action_type!=null){
            sql=sql+" and a.type="+action_type;
        }
        if(startDate!=null){
            sql=sql+" and b.date>='"+DateConverUtils.convertDateToString(startDate)+"'";
        }
        if(endDate!=null){
            sql=sql+" and b.date<='"+DateConverUtils.convertDateToString(endDate)+"'";
        }
        if(maxMoney!=null){
            sql=sql+" and b.money<="+maxMoney;
        }
        if(minMoney!=null){
            sql=sql+" and b.money>="+minMoney;
        }
        sql=sql+" order by b.date";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,user.getUser_id());
            
            rs=ps.executeQuery();
            
            while (rs.next()){
                int bill_id=rs.getInt("b.id");
                int user_id=rs.getInt("b.user_id");
                int naction_id=rs.getInt("b.action_id");
                float money=rs.getFloat("b.money");
                Date bill_date=DateConverUtils.convertStringToDate(rs.getString("b.date"));
                String note=rs.getString("b.note");
                Bill bill=new Bill(bill_id,user_id,naction_id,money,bill_date,note);
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
        String sql="delete  from bills where id=? and user_id=? ";   //防止非法操作，只有知道账单的编号，用户编号，事件编号才能删除
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bill.getId());
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

	public boolean checkActionIdExist(int action_id) {
		Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from bills where action_id=?";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,action_id);
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
        String sql="update bills set action_id=?, money=?, date=? ,note=? where id=? and user_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,bill.getAction_id());
            ps.setFloat(2,bill.getMoney());
            ps.setString(3,DateConverUtils.convertDateToString(bill.getDate()));
            ps.setString(4,bill.getNote());
            ps.setInt(5, bill.getId());
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
