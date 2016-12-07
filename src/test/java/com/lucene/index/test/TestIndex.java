package com.lucene.index.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lucene.bean.FileBean;
import com.lucene.index.FileBeanIndex;
import com.lucene.index.util.FileUtil;

public class TestIndex {
	public static void main(String[] args) {
		try {
			List<FileBean> fileBeans = FileUtil.getFolderFiles("D:\\test\\test80000");
			int totalCount = fileBeans.size();
			int perThreadCount = 1000;
			System.out.println("查询到的数据总数是" + fileBeans.size());
			int threadCount = totalCount / perThreadCount
					+ (totalCount % perThreadCount == 0 ? 0 : 1);
			ExecutorService pool = Executors.newFixedThreadPool(threadCount);
			CountDownLatch countDownLatch1 = new CountDownLatch(1);
			CountDownLatch countDownLatch2 = new CountDownLatch(threadCount);
			System.out.println(fileBeans.size());

			for (int i = 0; i < threadCount; i++) {
				int start = i * perThreadCount;
				int end = (i + 1) * perThreadCount < totalCount ? (i + 1)
						* perThreadCount : totalCount;
				List<FileBean> subList = fileBeans.subList(start, end);
				Runnable runnable = new FileBeanIndex("index", i,
						countDownLatch1, countDownLatch2, subList);
				// 子线程交给线程池管理
				pool.execute(runnable);
			}
			countDownLatch1.countDown();
			System.out.println("开始创建索引");
		Date start = new Date();

			// 等待所有线程都完成
			countDownLatch2.await();

			Date end = new Date();
			// 线程全部完成工作
			pool.shutdown();
			System.out.println("所有线程都创建索引完毕");

			// 释放线程池资源
			
			
			

			System.out.println("创建索引："+(end.getTime()-start.getTime()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void combineMoreIndex(int num) throws IOException {

	}
}
