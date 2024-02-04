package metersphere.Utils;


import com.alibaba.fastjson2.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MysqlUtils implements DatabaseUtils{
     Connection conn = null;
     Statement state = null;
     ResultSet rs = null;
     String connect = null;
     String user = null;
     String password = null;

    public MysqlUtils(String connect, String user, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connect = connect;
            this.user = user;
            this.password = password;
            connect();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库连接异常");
        }finally {
        }
    }

    private void connect() {
        try{
            conn = DriverManager.getConnection(connect, user, password);
            state = conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库连接异常");
        }
    }

    public Integer update (String sql){
        connect();
        Integer c = null;
        try {
            c = state.executeUpdate(sql);
        }catch (Exception e){
            System.out.println("修改失败"+sql);
        }finally {
            mysqlClose();
        }
         return c;
    }

    public Integer update (List<String> sqlList){
        connect();
        Integer c = 0;
        String sql = null;
        try{
            for(String item:sqlList){
                try {
                    sql = item;
                    c += state.executeUpdate(item);
                }catch (Exception e){
                    System.out.println("update error sql:"+sql);
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mysqlClose();
        }
        return c;
    }

    public String selectIdBySql (String sql){
//        connect();
        String fId = null;
        try{
            rs = state.executeQuery(sql);
            while (rs.next()){//判断是否有下一条数据
                fId =  rs.getString("id");
            }
        }catch (Exception e){
            System.out.println("查询字段id失败"+sql);
        }finally {
//            mysqlClose();
        }
        return fId;
    }

    public String selectValueBySQL(String sql){
        connect();
        String value = null;
        try{
            rs = state.executeQuery(sql);
            while (rs.next()){//判断是否有下一条数据
                value =  rs.getString("value");
            }
        }catch (Exception e){
            System.out.println("查询字段value失败"+sql);
        }finally {
            mysqlClose();
        }
        return value;
    }

    public String selectListBySQL(String sql){
        connect();
        String value = null;
        try{
            rs = state.executeQuery(sql);
            LinkedHashMap<String,List<Object>> map = new LinkedHashMap<>();
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
            mysqlClose();
        }
        return value;
    }

    @Override
    public String updateSQL(String sql) {
        return null;
    }


    //    public static void main(String[] args) {
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metersphere", "root", "feizhiyun");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        Statement state = null;
//        try{
//            state = conn.createStatement();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        String sql = "select * from api_definition";
//        Integer count = null;
//        LinkedHashMap<String,List<Object>> map = new LinkedHashMap<>();
//        try{
//            ResultSet rs = state.executeQuery(sql);
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int index = rsmd.getColumnCount();
//            for(int i = 0 ; i < index; i++){
//                String key = rsmd.getColumnName(i+1);
//                map.put(key, new ArrayList<Object>());
//            }
//            while (rs.next()){//判断是否有下一条数据
//                for(String key : map.keySet()){
//                    Object obj = rs.getObject(key);
//                    map.get(key).add(obj);
//                }
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(JSONObject.toJSONString(map));
//        try {
//            conn.close();
//            state.close();
//        }catch (Exception e){
//
//        }
//    }
    private void mysqlClose(){
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
