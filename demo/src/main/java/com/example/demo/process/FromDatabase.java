package com.example.demo.process;

import com.example.demo.model.Topic;

import java.sql.*;

public class FromDatabase {
    public static Topic GetQuestion(int ID) {
        /*//得到前端传来的id号
        IdFromHtml idFromHtml=new IdFromHtml();
        int ID=idFromHtml.ReturnId();*/

        Topic topic=new Topic();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        topic.setId(ID);
        try {
            // 加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            long start = System.currentTimeMillis();

            // 建立连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/topic",
                    "root", "123456");
            long end = System.currentTimeMillis();
            System.out.println(conn);
            System.out.println("建立连接耗时： " + (end - start) + "ms 毫秒");

            // 创建Statement对象
            stmt = conn.createStatement();

            // 执行SQL语句
            rs = stmt.executeQuery("select question from topic where id="+ID);
            //System.out.println("name");
            while (rs.next()) {
                topic.setQuestion(rs.getString(1));
            }
            //System.out.println("111:"+topic.getQuestion());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return topic;
    }
 /*   public static void main(String args[]){
        Topic topi = Test1.GetQuestion(1);
    }*/
}
