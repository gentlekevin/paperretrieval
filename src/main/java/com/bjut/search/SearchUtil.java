package com.bjut.search;

public class SearchUtil {

	 /**
	   * 根据作者所在的序列，给定一分数
	   * @param i author 的序列
	   * @return 
	   */
	  public static float getScoreByFPAuthorSequence(int i){
		  
		  float score =0;
			switch(i){
			case 1:score=2f;break;
			case 2:score=1f;break;
			case 3:score=0.5f;break;
			default:score=0.1f ;break;
			}
		  
		return score;
		  
	  }
	public static float getScoreByAuthorSequence(int i){
		  
		  float score =0;
			switch(i){
			case 1:score=1;break;
			case 2:score=0.5f;break;
			case 3:score=0.2f;break;
				
			default:score=0.1f ;break;
			}
		  
		return score;
		  
	  }
	
}
