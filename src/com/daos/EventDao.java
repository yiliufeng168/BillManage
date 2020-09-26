package com.daos;


import com.beans.Eventr;
import com.beans.User;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDao {

    public int registEv(Eventr eventr) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="insert into events (user_id,event_name,event_type) values (?,?,?)";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,eventr.getUser_id());
            ps.setString(2,eventr.getEvent_name());
            ps.setInt(3,eventr.getEvent_type());
            ps.executeUpdate();
            rs=ps.executeQuery("SELECT LAST_INSERT_ID() as id ");
            if(rs.next() ) {
            	return rs.getInt("id");
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }
        return -1;
    }


    public boolean modifyEv(Eventr eventr) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="update events set event_name=?,event_type=? where event_id=?";
        int rs=0;
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,eventr.getEvent_name());
            ps.setInt(2,eventr.getEvent_type());
            ps.setInt(3,eventr.getEvent_id());
            rs=ps.executeUpdate();
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

    public List<Eventr> getAllEvents(User user) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from events where user_id=?";
        List<Eventr> eventrs =new ArrayList<Eventr>();
        ResultSet rs=null;

        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,user.getUser_id());
            rs=ps.executeQuery();
            while (rs.next()){
                int event_id=rs.getInt("event_id");
                int user_id=rs.getInt("user_id");
                String event_name=rs.getString("event_name");
                int event_type=rs.getInt("event_type");
                Eventr eventr=new Eventr(event_id,user_id,event_name,event_type);
                eventrs.add(eventr);
            }
            return eventrs;
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(rs, ps, conn);
        }
        return null;
    }

    public boolean delEv(Eventr eventr) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="delete  from events where event_id=? and user_id=? and event_name=?";   //防止非法操作，只有知道事件的编号，用户编号，事件名才能删除
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,eventr.getEvent_id());
            ps.setInt(2,eventr.getUser_id());
            ps.setString(3,eventr.getEvent_name());
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


	public List<Eventr> getAllEvents(User loginUser, int eventInt) {
		Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from events where user_id=? and event_type=?";
        List<Eventr> eventrs =new ArrayList<Eventr>();
        ResultSet rs=null;

        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,loginUser.getUser_id());
            ps.setInt(2, eventInt);
            rs=ps.executeQuery();
            while (rs.next()){
                int event_id=rs.getInt("event_id");
                int user_id=rs.getInt("user_id");
                String event_name=rs.getString("event_name");
                int event_type=rs.getInt("event_type");
                Eventr eventr=new Eventr(event_id,user_id,event_name,event_type);
                eventrs.add(eventr);
            }
            return eventrs;
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(rs, ps, conn);
        }
        return null;
	}


	public boolean checkEventExist(Eventr eventr) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from events where user_id=? and event_name=?";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,eventr.getUser_id());
            ps.setString(2, eventr.getEvent_name());
            rs=ps.executeQuery();

            if(rs.next()){
                //存在
                return true;
            }else {
                //不存在
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
}
