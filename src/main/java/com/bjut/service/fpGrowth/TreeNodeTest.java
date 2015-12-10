package com.bjut.service.fpGrowth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class TreeNodeTest {
     
    private TreeNodeTest parent;
    private String name;
    private List<String>papers;
  
    private Set<TreeNodeTest> children;
     
    public TreeNodeTest(TreeNodeTest parent,String name,List<String> papers){
        
        this.parent = parent;
        this.name = name;
        this.papers = papers;
    }
     
    public TreeNodeTest(String name,List<String> papers){
        this.name = name;
        this.papers = papers;
    }
 
    /**
     * 父节点是否包含子节点包含则返回，否则返回null
     * @param key
     * @return
     */
    public TreeNodeTest findChild(String key){
        if(this.children == null){
            return null;
        }
        for(TreeNodeTest child:this.children){
            if(StringUtils.equals(child.name,key)){
                return child;
            }
        }
        return null;
    }
    /**
     * 给父节点增加一个子节点
     * @param child
     * @return
     */
    public TreeNodeTest addChild(TreeNodeTest child){
        if(this.children == null){
            this.children = new HashSet<TreeNodeTest>();
        }
        this.children.add(child);
        return child;
    }
    public boolean isEmpty(){
        return this.children==null || this.children.size()==0;
    }
     
    public TreeNodeTest getParent() {
        return parent;
    }
    public void setParent(TreeNodeTest parent) {
        this.parent = parent;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
  
 
    public Set<TreeNodeTest> getChildren() {
        return children;
    }
 
    public void setChildren(Set<TreeNodeTest> children) {
        this.children = children;
    }

	public List<String> getPapers() {
		return papers;
	}

	public void setPapers(List<String> papers) {
		this.papers = papers;
	}
     
}
