package com.lucene.index.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class merge {

	public static void main(String[] args) throws IOException {
		
		long mbegintime = System.nanoTime();// 纳秒
		
		
		Directory d3 = FSDirectory.open(Paths.get("index\\indexTotal"));// 合并到索引3里面

		IndexWriter writer = new IndexWriter(d3, new IndexWriterConfig(	new StandardAnalyzer()));
		try {
			for (int i = 0; i < 45; i++) {
				Directory d = FSDirectory.open(Paths.get("index\\index" + i));

				writer.addIndexes(d);// 传入各自的Diretory或者IndexReader进行合并
			}
			writer.commit();// 提交索引
			writer.close();
			System.out.println("合并索引完毕.........");

		} catch (Exception e) {
			e.printStackTrace();
		}
		long mendtime = System.nanoTime();// 纳秒
		
		BigDecimal diff = BigDecimal.valueOf(mendtime - mbegintime, 10);// 秒级差值
		
		double time = diff.setScale(4, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		System.out.println("创建索引："+diff);

	}

}
