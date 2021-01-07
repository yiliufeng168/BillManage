package com;

import com.utils.JdbcUtils;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InitSystem {
    public InitSystem() {
        //检查表是否存在，
        try {
            DatabaseMetaData metaData= JdbcUtils.getConnection().getMetaData();

            ResultSet bill = metaData.getTables("dxq_db", null, "bill", null);
            if (!bill.next()){
                System.out.println("表不存在");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //不存在就建表
    }
}
