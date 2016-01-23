package com.bjut.search.field;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;


public class SingleDucument{        
    private static Document document = null;        
    private SingleDucument(){   }        
    public static Document getInstance(){        
        if (document == null)        
        	document = new Document();         
                return document;         
    }        
} 