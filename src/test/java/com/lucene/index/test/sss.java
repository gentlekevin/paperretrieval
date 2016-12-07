package com.lucene.index.test;

import java.math.BigDecimal;

public class sss {

	public static void main(String[] args) throws InterruptedException {
long mbegintime = System.nanoTime();// 纳秒


long mendtime = System.nanoTime();// 纳秒
		BigDecimal diff = BigDecimal.valueOf(mendtime - mbegintime, 10);// 秒级差值
		
		for(int i = 0;i<1000000;i++){
			System.out.println("ssss");	
		}
		
		double time = diff.setScale(4, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		System.out.println(time);

	}

}
