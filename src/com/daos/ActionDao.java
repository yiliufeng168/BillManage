package com.daos;


import com.beans.Action;
import com.beans.User;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActionDao {

    //添加动作,返回ID
    public int addAction(Action action) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="insert into actions (owner_id,name,type ) values (?,?,?)";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1, action.getOwner_id());
            ps.setString(2, action.getName());
            ps.setInt(3, action.getType());
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

    //编辑动作
    public boolean editAction(Action action) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="update actions set name=?,type=? where id=?";
        int rs=0;
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1, action.getName());
            ps.setInt(2, action.getType());
            ps.setInt(3, action.getId());
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



    //删除动作
    public boolean delAction(Action action) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="delete  from actions where id=? and owner_id=?";   //防止非法操作，只有知道事件的编号，用户编号，才能删除
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1, action.getId());
            ps.setInt(2, action.getOwner_id());
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

    //获取所有动作
    public List<Action> getAllActions(User user) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from actions where owner_id=?";
        List<Action> actions =new ArrayList<Action>();
        ResultSet rs=null;

        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,user.getUser_id());
            rs=ps.executeQuery();
            while (rs.next()){
                int id=rs.getInt("id");
                int owner_id=rs.getInt("owner_id");
                String name=rs.getString("name");
                int type=rs.getInt("type");
                Action action =new Action(id,owner_id,name,type);
                actions.add(action);
            }
            return actions;
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(rs, ps, conn);
        }
        return null;
    }

    //获取所有动作，参数为
	public List<Action> getAllActions(User loginUser, int action_type) {
		Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from actions where owner_id=? and type=?";
        List<Action> actions =new ArrayList<Action>();
        ResultSet rs=null;

        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,loginUser.getUser_id());
            ps.setInt(2, action_type);

            rs=ps.executeQuery();
            while (rs.next()){
                int id=rs.getInt("id");
                int owner_id=rs.getInt("owner_id");
                String name=rs.getString("name");
                int type=rs.getInt("type");
                Action action =new Action(id,owner_id,name,type);

                actions.add(action);
            }

            return actions;
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(rs, ps, conn);
        }
        return null;
	}

    //检查动作是否存在
	public boolean checkActionExist(Action action) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from actions where owner_id=? and name=?";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1, action.getOwner_id());
            ps.setString(2, action.getName());
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

    public Action getAction(Integer action_id) {
        Connection conn= JdbcUtils.getConnection();
        PreparedStatement ps=null;
        String sql="select * from actions where id=? ";
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1, action_id);
            rs=ps.executeQuery();
            if(rs.next()){
                //存在
                int id=rs.getInt("id");
                int owner_id=rs.getInt("owner_id");
                String name=rs.getString("name");
                int type=rs.getInt("type");
                Action action =new Action(id,owner_id,name,type);
                return action;
            }else {
                //不存在
                return null;
            }

        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseConnection(rs, ps, conn);
        }
        return null;

    }
}
