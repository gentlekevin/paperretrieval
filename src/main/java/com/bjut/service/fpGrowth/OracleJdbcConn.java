package com.bjut.service.fpGrowth;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OracleJdbcConn {

 public static void main(String[] args) {
  ResultSet rs = null;
  Statement stmt = null;
  Connection conn = null;
  List<String> frequences = new LinkedList<String>();
  List<List<String>> transactions = new ArrayList<List<String>>();
  //开始递归
  
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
   //new oracle.jdbc.driver.OracleDriver();
   conn = DriverManager.getConnection("jdbc:oracle:thin:@172.21.7.9:1521:bjutailib", "bjut", "bjut");
   stmt = conn.createStatement();
   rs = stmt.executeQuery("select paperid,authors from T_PAPER_AUTHOR");
   
   long count = 0;
   while(rs.next()) {
   
    List<String> transaction = new ArrayList<String>();
    long paperId =rs.getLong("paperid");
    String []authors = rs.getString("authors").split(",");
    for(String author:authors){
    	transaction.add(author+"#"+paperId);
    }
    transactions.add(transaction);
    count++;
    if(count%1000==0){
    	System.out.println(count);
    }
    if(count>10000) break;
    //System.out.println(rs.getInt("deptno"));
   }
   
   FpGrowthTest.digTree(transactions,frequences,conn,stmt);
   conn.commit();
  } catch (ClassNotFoundException e) {
   e.printStackTrace();
  } catch (SQLException e) {
   e.printStackTrace();
  } finally {
   try {
    if(rs != null) {
     rs.close();
     rs = null;
    }
    if(stmt != null) {
     stmt.close();
     stmt = null;
    }
    if(conn != null) {
     conn.close();
     conn = null;
    }
   } catch (SQLException e) {
    e.printStackTrace();
   }
  }
  
  
  
  
 }

}



