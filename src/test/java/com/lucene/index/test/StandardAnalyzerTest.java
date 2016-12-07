package com.lucene.index.test;


import java.util.Iterator;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class StandardAnalyzerTest {

	public static void main(String[] args) {
		try {
			
			String text = "龙战军 温良英 张生富";

			

			// 标准分词器(Lucene内置的标准分析器,会将语汇单元转成小写形式，并去除停用词及标点符号)
			WhitespaceAnalyzer sa = new WhitespaceAnalyzer( );

			TokenStream ts = sa.tokenStream("field", text);
			CharTermAttribute ch = ts.addAttribute(CharTermAttribute.class);

			ts.reset();
			while (ts.incrementToken()) {
				System.out.println(ch.toString());
			}
			ts.end();
			ts.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
