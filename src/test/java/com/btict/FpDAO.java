package com.btict;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bjut.entity.AuthorsPapers;
import com.bjut.entity.Paper;
public class FpDAO {

	public static List<AuthorsPapers> findFpPaperIdsByIds(List<String>ids){
		
		  ResultSet rs = null;
		  Statement stmt = null;
		  Connection conn = null;
		  List<AuthorsPapers>papers = new ArrayList<AuthorsPapers>();
		  
		  try {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   //new oracle.jdbc.driver.OracleDriver();
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@172.21.7.9:1521:bjutailib", "bjut", "bjut");
		   stmt = conn.createStatement();
		   for(String paperId:ids){
		   rs = stmt.executeQuery("select authors,papers from T_authors_papers where id="+paperId);
				
		   while(rs.next()) {
		   AuthorsPapers paper = new AuthorsPapers();
		   paper.setAuthors(rs.getString("authors"));
		   
		   paper.setPapers(rs.getString("papers"));
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
	
	public static List<String> findPaperIdsByAuthor(String author){
		
		  ResultSet rs = null;
		  Statement stmt = null;
		  Connection conn = null;
		  List<String>paperIds = new ArrayList<String>();
		  
		  try {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		   //new oracle.jdbc.driver.OracleDriver();
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@172.21.7.9:1521:bjutailib", "bjut", "bjut");
		   stmt = conn.createStatement();
		   
		   rs = stmt.executeQuery("select papers from T_fp_author_papers where author='"+author+"'");
				
		   while(rs.next()) {
			   for(String id:rs.getString("papers").split(",")){
				   if(id!=null&&!"".equals(id))
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
