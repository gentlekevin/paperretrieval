package com.bjut.service.fpGrowth;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateKeywordPapers {

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
   rs = stmt.executeQuery("select id,keyword from T_PAPER_keyword");
   
   long count = 0;
   Map<String, String> map = new HashMap<String, String>();
   String paperId;
   String []keywords;
   while(rs.next()) {
   
     paperId =rs.getString("id");
     if(rs.getString("keyword")==null||"".equals(rs.getString("keyword")))continue;
     keywords = rs.getString("keyword").split(" ");
   for(String keyword:keywords){
	   if(keyword.contains("模式识别")){
		   System.out.println(keyword);
	   }
	   
	   if(map.containsKey(keyword)){
		   if( map.get(keyword).length()>3500)break;
		   map.put(keyword, map.get(keyword)+","+paperId);
	   }else{
		   map.put(keyword, paperId);
	   }
   }
   count++;
   if(count%1000==0){
   	System.out.println("共处理了："+count);
   	
   } 
   if(count>100000) break;
   }
   String key=null;
count=0;
    Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");  
    Matcher matcher = null;  
   for (Entry<String, String> entry : map.entrySet()) {  
	   
	   key= entry.getKey();
	   key = key.replaceAll("'", "");
	   key = key.replaceAll(",", "");
	   matcher = pattern.matcher("key");
	 
	    if(matcher.matches()) continue;
	    stmt.execute("insert into t_keyword_papers values(t_keyword_papers_id.nextval,'"+key+"','"+entry.getValue()+"')");
	  
	  
    count++;
    if(count%1000==0){
    	System.out.println("插入了："+count);
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



