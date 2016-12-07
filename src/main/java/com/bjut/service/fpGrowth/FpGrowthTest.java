package com.bjut.service.fpGrowth;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
public class FpGrowthTest {
	private static int support = 3;
    private static long count=0;
    public static void main(String[] args) throws IOException{
        //从文件中读取事物数据集
    	  String file="e://test1.txt";
        Iterator<String> lineIte = FileUtils.lineIterator(new File(file));
        List<List<String>> transactions = new ArrayList<List<String>>();
        while(lineIte.hasNext()){
            String line = lineIte.next();
            if(StringUtils.isNotEmpty(line)){
            	String[]idAuthors = line.split("#");
            	
                String[] subjects = idAuthors[1].split(",");
                for(int i=0;i<subjects.length;i++){
                	subjects[i] = subjects[i]+"#"+idAuthors[0];
                }
                List<String> list = new ArrayList<String>(Arrays.asList(subjects));
                transactions.add(list);
            }
        }
        //初始一个频繁模式集
        List<String> frequences = new LinkedList<String>();
        //开始递归
        //digTree(transactions,frequences);
    }
   
    public static void digTree(List<List<String>> transactions,
            List<String> frequences,Connection conn,Statement stmt){
        //扫描事物数据集，排序
        final Map<String,Integer> sortedMap = scanAndSort(transactions);
        //没有数据是支持最小支持度了，可以停止了
        if(sortedMap.size() == 0){
            return;
        }
        Map<String,List<TreeNodeTest>> index = new HashMap<String,List<TreeNodeTest>>();
        TreeNodeTest root = buildTree(transactions,index,sortedMap);
       //否则开始从排序最低的项开始 抽出条件模式基，递归挖掘
        List<String> headTable = new ArrayList<String>(sortedMap.keySet());
        Collections.sort(headTable,new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                int i = sortedMap.get(o2)-sortedMap.get(o1); 
                return i != 0 ? i : o1.compareTo(o2);
            }});
        //从项头表最后一项开始挖掘
        for(int i=headTable.size()-1;i>=0;i--){ 
            String subject = headTable.get(i);
            List<List<String>> frequentModeBases = extract(index.get(subject),root);
             
            LinkedList<String> nextFrequences = new LinkedList<String>(frequences);
            nextFrequences.add(subject);
          
            if(nextFrequences.size()>1){
            	
            	  StringBuffer papers = new StringBuffer();
            	for(String paperid:index.get(subject).get(0).getPapers()){
            		papers.append(paperid+",");
            		
            	}
            	String sql="insert into t_authors_papers values(t_authors_papers_id.nextval,'"+StringUtils.join(nextFrequences,",")+"','"+papers.toString()+"')";
            	 System.out.println(sql);   
               
                	//每一千条提交一下
                	try {
                		 stmt.execute(sql);
                		 count++;
                		 if(count%1000==0){
                			 conn.commit();	 
                		 }					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
             
            digTree(frequentModeBases,nextFrequences,conn,stmt);
             
        }
        
    }
    
    /**
     * 挖掘一个项上面的频繁模式基
     * @param list
     * @param root
     * @return
     */
    public static List<List<String>> extract(List<TreeNodeTest> list,TreeNodeTest root){
        List<List<String>> returnList = new ArrayList<List<String>>();
        for(TreeNodeTest node:list){
        	 for(String paperId:node.getPapers()){
            TreeNodeTest parent = node.getParent();
            if(parent.getPapers()!=null&&parent.getPapers().size() != -1){               
               
                	ArrayList<String> tranc = new ArrayList<String>();
                
                while(parent.getPapers()!=null&&parent.getPapers().size() != -1){
                    tranc.add(parent.getName()+"#"+paperId);
                    parent = parent.getParent();
                 }
                returnList.add(tranc);
                }
                
            }
        }
        return returnList;
    }
    
    /**
     * 构建pf树
     * @param file
     * @param index
     * @param sortedMap
     * @return
     * @throws IOException
     */
    public static TreeNodeTest buildTree(List<List<String>> transactions,
            Map<String,List<TreeNodeTest>> index,
            final Map<String,Integer> sortedMap){
        TreeNodeTest root = new TreeNodeTest(null,"root",null);
        for(List<String> subjects:transactions){
        	
            Iterator<String> ite = subjects.iterator();
            while(ite.hasNext()){
                String[] ap = ite.next().split("#");
                if(!sortedMap.containsKey(ap[0])){
                    ite.remove();
                }
            }
            Collections.sort(subjects,new Comparator<String>(){
                @Override
                public int compare(String o1, String o2) {
                	String [] o1Temp = o1.split("#");
                	String [] o2Temp = o2.split("#");
                    int i = sortedMap.get(o2Temp[0])-sortedMap.get(o1Temp[0]); 
                    return i != 0 ? i : o1Temp[0].compareTo(o2Temp[0]);
                }});
             
            TreeNodeTest current = root;
            for(int i=0;i<subjects.size();i++){
                String[] subject = subjects.get(i).split("#");
                
                TreeNodeTest next = current.findChild(subject[0]);
                if(next == null){
                	List<String>papers = new ArrayList<String>();
                	papers.add(subject[1]);
                    TreeNodeTest newNode = new TreeNodeTest(current,subject[0].split("#")[0],papers);
                    current.addChild(newNode);
                    current = newNode;
                    List<TreeNodeTest> thisIndex = index.get(subject[0]);
                    if(thisIndex == null){
                        thisIndex = new ArrayList<TreeNodeTest>();
                        index.put(subject[0], thisIndex);
                    }
                    thisIndex.add(newNode);
                }else{
                    
                    next.getPapers().add(subject[1]);
                    current = next;
                }
            }
        }
        return root;
    }
    
    /**
     * 扫描排序
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<String,Integer> scanAndSort(List<List<String>> transactions){
        Map<String,Integer> map = new HashMap<String,Integer>();
        //空的就不扫了
        if(transactions.size()==0){
            return map;
        }
        for(List<String> basket:transactions){
            for(String subject:basket){
            	String []authorsPaperId = subject.split("#");
                Integer count = map.get(authorsPaperId[0]);
                if (count == null) {
                    map.put(authorsPaperId[0], 1);
                } else {
                    map.put(authorsPaperId[0], count + 1);
                }
            }
        }
        Iterator<Entry<String,Integer>> ite = map.entrySet().iterator();
        while(ite.hasNext()){
            Entry<String,Integer> entry = ite.next();
            if(entry.getValue() < support){
                ite.remove();
            }
        }
        return map;
    }
    /**
     * 
     * @param transactions[[author1#p1,author2#p1],[author1#p2,author3#p2],[]...]
     * @return  author1:p1,p2,p3....(pi:paperid)
     */
    public static Map<String,List<String>> scanAndSortTest(List<List<String>> transactions){
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        //空的就不扫了
        if(transactions.size()==0){
            return map;
        }
        for(List<String> basket:transactions){
            for(String subject:basket){
            	//得到作者的所有的论文集合
            	String[]subjectTemp  = subject.split("#");
                List<String> p = map.get(subjectTemp[0]);
                if (p == null) {
                	//新建作者：论文集合放进map
                	p = new ArrayList<String>();
                	p.add(subjectTemp[1]);
                    map.put(subjectTemp[0], p);
                } else {
                	//如果map中已经存在该作者，则更新该作者的论文集合
                	p.add(subjectTemp[1]);
                    map.put(subjectTemp[0], p);
                }
            }
        }
        Iterator<Entry<String,List<String>>> ite = map.entrySet().iterator();
        while(ite.hasNext()){
            Entry<String,List<String>> entry = ite.next();
            if(entry.getValue().size() < support){
                ite.remove();
            }
        }
        return map;
    }
}
