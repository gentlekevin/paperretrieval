package com.bjut.service.fpGrowth;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CreateFpAuthorPapers {

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
   rs = stmt.executeQuery("select authors,papers from T_authors_papers");
   
   long count = 0;
   Map<String, String> map = new HashMap<String, String>();
  
   String []authors;
   while(rs.next()) {
   
	   authors =rs.getString("authors").split(",");
    authors = rs.getString("authors").split(",");
   for(String author:authors){
	   
	   
	   if(map.containsKey(author)){
		   if( map.get(author).length()+rs.getString("papers").length()>3999)break;
		   map.put(author, map.get(author)+","+rs.getString("papers"));
	   }else{
		   map.put(author, rs.getString("papers"));
	   }
   }
   count++;
   if(count%100000==0){
   	System.out.println("共处理了："+count);
   	
   } 
   
   }
  
count=0;
   for (Entry<String, String> entry : map.entrySet()) {  
	    
	   	
	   // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
	   try{
		   stmt.execute("insert into t_fp_author_papers values(t_fp_author_papers_id.nextval,'"+entry.getKey()+"','"+entry.getValue()+"')");
	   }catch(SQLException e){
		   System.out.println("出错的是："+entry.getKey()+","+entry.getValue());
	   }
	    
	  
	  
    count++;
    if(count%1000==0){
    	System.out.println("共插入了："+count);
    	conn.commit();
    }
 
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



