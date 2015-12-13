package com.bjut.service.fpGrowth;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CreateAuthorPapers {

 public static void main(String[] args) {
  ResultSet rs = null;
  Statement stmt = null;
  Connection conn = null;
 
  
  //开始递归
  
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
   //new oracle.jdbc.driver.OracleDriver();
   conn = DriverManager.getConnection("jdbc:oracle:thin:@172.21.7.9:1521:bjutailib", "bjut", "bjut");
   stmt = conn.createStatement();
   rs = stmt.executeQuery("select paperid,authors from T_PAPER_AUTHOR");
   
   long count = 0;
   Map<String, String> map = new HashMap<String, String>();
   long paperId;
   String []authors;
   while(rs.next()) {
   
     paperId =rs.getLong("paperid");
    authors = rs.getString("authors").split(",");
   for(String author:authors){
	   
	   if(map.containsKey(author)){
		   map.put(author, map.get(author)+","+paperId);
	   }else{
		   map.put(author, author);
	   }
   }
   String key=null;
   for (Entry<String, String> entry : map.entrySet()) {  
	   
	   key= entry.getKey();
	   key = key.replace("'", "");
	   key = key.replace(",", "");
	   	
	    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
	    stmt.execute("insert into t_author_papers values(t_author_papers_id.nextval,"+key+"','"+entry.getValue()+"')");
	  
	}  
    count++;
    if(count%1000==0){
    	System.out.println(count);
    	conn.commit();
    }
    if(count>1000) break;
    
     }
 
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



