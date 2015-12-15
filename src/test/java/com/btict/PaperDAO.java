package com.btict;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bjut.entity.Paper;
public class PaperDAO {

	public static List<Paper> findPaperByIds(List<String> ids){
		
		  ResultSet rs = null;
		  Statement stmt = null;
		  Connection conn = null;
		  List<Paper>papers = new ArrayList<Paper>();
		  //开始递归
		  
		  try {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   //new oracle.jdbc.driver.OracleDriver();
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@172.21.7.9:1521:bjutailib", "bjut", "bjut");
		   stmt = conn.createStatement();
		   for(String paperId:ids){
		   rs = stmt.executeQuery("select id,title,keyword,author from T_Paper where id="+paperId);
				
		   while(rs.next()) {
		   Paper paper = new Paper();
		   paper.setId(rs.getLong("id"));
		   paper.setTitle(rs.getString("title"));
		   paper.setAuthor(rs.getString("author"));
		   paper.setKeyword(rs.getString("keyword"));
		   papers.add(paper);
		   
		   }
		   }
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
		  return papers;

	}
	
	public static List<String> findPaperIdByAuhtor(String author){
		
		  ResultSet rs = null;
		  Statement stmt = null;
		  Connection conn = null;
		  List<String>paperIds = new ArrayList<String>();
		  
		  //开始递归
		  
		  try {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   //new oracle.jdbc.driver.OracleDriver();
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@172.21.7.9:1521:bjutailib", "bjut", "bjut");
		   stmt = conn.createStatement();		 
		   rs = stmt.executeQuery("select papers from T_author_papers where author='"+author+"'");
		   while(rs.next()) {
			   String idsTmp = rs.getString("papers");
			   idsTmp = idsTmp.trim();			   
			   String [] pids = idsTmp.split(",");
			   for(String id:pids){
				   paperIds.add(id);   
			   }			
		      }		   
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
		  return paperIds;

	}
	
	public static List<String> findPaperIdByKeyword(String keyword){
		
		  ResultSet rs = null;
		  Statement stmt = null;
		  Connection conn = null;
		  List<String>paperIds = new ArrayList<String>();
		  
		  //开始递归
		  
		  try {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   //new oracle.jdbc.driver.OracleDriver();
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@172.21.7.9:1521:bjutailib", "bjut", "bjut");
		   stmt = conn.createStatement();		 
		   rs = stmt.executeQuery("select papers from T_keyword_papers where keyword='"+keyword+"'");
		   while(rs.next()) {
			   String idsTmp = rs.getString("papers");
			   idsTmp = idsTmp.trim();			   
			   String [] pids = idsTmp.split(",");
			   for(String id:pids){
				   paperIds.add(id);   
			   }			
		      }		   
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
		  return paperIds;

	}
	
}
