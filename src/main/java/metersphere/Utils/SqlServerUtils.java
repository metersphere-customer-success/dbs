package metersphere.Utils;

import com.alibaba.fastjson2.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SqlServerUtils implements DatabaseUtils {
    Connection conn = null;
    Statement state = null;
    ResultSet rs = null;
    String connect = null;
    String user = null;
    String password = null;
    @Override
    public String selectListBySQL(String sql){
        connect();
        String value = null;
        try{
            rs = state.executeQuery(sql);
            LinkedHashMap<String, List<Object>> map = new LinkedHashMap<>();
            try {
                ResultSet rs = state.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                int index = rsmd.getColumnCount();
                for (int i = 0; i < index; i++) {
                    String key = rsmd.getColumnName(i + 1);
                    map.put(key, new ArrayList<Object>());
                }
                while (rs.next()) {//判断是否有下一条数据
                    for (String key : map.keySet()) {
                        Object obj = rs.getObject(key);
                        map.get(key).add(obj);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            value = JSONObject.toJSONString(map);
        }catch (Exception e){
            System.out.println("查询字段value失败"+sql);
        }finally {
            sqlServerClose();
        }
        return value;
    }

    public String updateSQL(String sql) {
        connect();
        int i = 0;
        try{
            System.out.println(sql);
            i = state.executeUpdate(sql);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("修改字段value失败"+sql);
        }finally {
            sqlServerClose();
        }
        return i==0 ? "更新失败" : "更新成功";
    }

//    @Override
//    public String selectAllTable() {
//        return null;
//    }

    private void connect() {
        try{
            conn = DriverManager.getConnection(connect, user, password);
            state = conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库连接异常");
        }
    }
    public SqlServerUtils(String connect, String user, String password){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connect = connect;
            this.user = user;
            this.password = password;
            connect();
        }catch (Exception e){
            System.out.println("数据库连接异常");
        }finally {
        }
    }

    private void sqlServerClose(){
        try {
            rs.close();
        }catch (Exception e){}
        try {
            state.close();
        }catch (Exception e){}
        try {
            conn.close();
        }catch (Exception e){}
    }
}
