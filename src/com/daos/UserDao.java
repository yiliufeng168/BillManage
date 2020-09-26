package com.daos;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.beans.User;
import com.utils.JdbcUtils;

public class UserDao {

    //注册
    public void regist(User user) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="insert into users (user_name,user_password,user_age,user_phone,user_sex) values (?,?,?,?,?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getUser_name());
            ps.setString(2,user.getUser_password());
            ps.setInt(3,user.getUser_age());
            ps.setString(4,user.getUser_phone());
            ps.setString(5,user.getUser_sex());
            int rs=ps.executeUpdate();
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }

    }

    //登陆
    public User login(User user) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from users where user_name=? and user_password=?";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getUser_password());
            rs=ps.executeQuery();
            if(rs.next()){
                int user_id=rs.getInt("user_id");
                String user_name=rs.getString("user_name");
                String user_password=rs.getString("user_password");
                int user_age=rs.getInt("user_age");
                String user_phone=rs.getString("user_phone");
                String user_sex=rs.getString("user_sex");
                user=new User(user_id,user_name,user_password,user_age,user_phone,user_sex);
                return user;
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }
        return null;
    }


    public int modifyInfo(User user) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="update users set user_age=?,user_phone=?,user_sex=? where user_id=?";
        int rs=0;
        try{	
            ps=conn.prepareStatement(sql);
            ps.setInt(1, user.getUser_age());
            ps.setString(2,user.getUser_phone());
            ps.setString(3,user.getUser_sex());
            ps.setInt(4,user.getUser_id());
            rs=ps.executeUpdate();
            return rs;
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }
        return 0;

    }

    public boolean checkUserName(String user_name) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from users where user_name=?";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,user_name);
            rs=ps.executeQuery();

            if(rs.next()){
                //已注册
                return false;
            }else {
                //未注册
                return true;
            }

        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(rs, ps, conn);
        }
        return true;
    }

	public void modifyPassword(User user) {
		// TODO 自动生成的方法存根
		Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="update users set user_password=? where user_id=?";
        int rs=0;
        try{	
            ps=conn.prepareStatement(sql);          
            ps.setString(1,user.getUser_password());
            ps.setInt(2,user.getUser_id());
            rs=ps.executeUpdate();
            
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(null, ps, conn);
        }
	}
}
