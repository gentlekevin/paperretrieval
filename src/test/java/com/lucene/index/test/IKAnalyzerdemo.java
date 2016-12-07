/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package com.lucene.index.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;




/**
 * 使用IKAnalyzer进行Lucene索引和查询的演示
 * 2012-3-2
 * 
 * 以下是结合IKAnalyzerdemo API的写法
 *
 */
public class IKAnalyzerdemo {
	
	
	/**
	 * 模拟：
	 * 创建一个单条记录的索引，并对其进行搜索
	 * @param args
	 */
	public static void main(String[] args){
		//Lucene Document的域名
		String fieldName = "text";
		 //检索内容
		String text1 = "oracle,数据库";
		String text2 = "数据库系统的本质是什么";
		String text3 = "数据库的本质是文件系统";
		
		//实例化IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer();
		
		Directory directory1 = null;
		Directory directory2 = null;
		IndexWriter iwriter1 = null;
		IndexWriter iwriter2 = null;
		IndexReader ireader1 = null;
		IndexReader ireader2 = null;
		IndexSearcher isearcher = null;
		try {
			//建立内存索引对象
			directory1 = new RAMDirectory();
			directory2 = new RAMDirectory();
			
			//配置IndexWriterConfig
			
			IndexWriterConfig iwConfig1 = new IndexWriterConfig(analyzer);
			iwConfig1.setOpenMode(OpenMode.CREATE);
			
			IndexWriterConfig iwConfig2 = new IndexWriterConfig(analyzer);
			iwConfig2.setOpenMode(OpenMode.CREATE);
			iwriter1 = new IndexWriter(directory1 , iwConfig1);
			iwriter2 = new IndexWriter(directory2 , iwConfig2);
			
			//写入索引
			Document doc1 = new Document();
			doc1.add(new StringField("ID", "10000", Field.Store.YES));
			doc1.add(new TextField("text1", text1, Field.Store.YES));
			iwriter1.addDocument(doc1);
			
			Document doc2 = new Document();
			doc2.add(new StringField("ID", "10001", Field.Store.YES));
			doc2.add(new TextField("text2", text2, Field.Store.YES));
			iwriter2.addDocument(doc2);
			
			
			iwriter1.close();
			iwriter2.close();
			
			
			//搜索过程**********************************
		    //实例化搜索器   
			ireader1 = DirectoryReader.open(directory1);
			ireader2 = DirectoryReader.open(directory2);
			
			IndexReader []mreader = {ireader1,ireader2};
			
			MultiReader multiReader = new MultiReader(mreader);
			
				isearcher = new IndexSearcher(multiReader);	
			
			String keyword ="数据库的本质是什么";			
			//使用QueryParser查询分析器构造Query对象
			String [] fields = {"text1","text2"};
			
			
			
			 Map<String , Float> boosts = new HashMap<String, Float>();
		        boosts.put("text1", 5.0f);
		        boosts.put("text2", 2.0f);
		        /**用MultiFieldQueryParser类实现对同一关键词的跨域搜索 
		         * */
		        MultiFieldQueryParser  parser = new MultiFieldQueryParser(fields, analyzer, boosts);
		       Query query = parser.parse(keyword);
			
		       
			
			
			
			System.out.println("Query = " + query);
			
			//搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query , 5);
			System.out.println("命中：" + topDocs.totalHits);
			//输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++){
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("内容：" + targetDoc.toString());
			}			
			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally{
			if(ireader1 != null){
				try {
					ireader1.close();
					ireader2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(directory1 != null){
				try {
					directory1.close();
					directory2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
