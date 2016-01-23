package com.bjut.search.field;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public class SingleKeywordField{        
    private static TextField textField = null;        
    private SingleKeywordField(){   }        
    public static TextField getInstance(){        
        if (textField == null)        
        	textField = new TextField("keyword","keyword" , Field.Store.NO);         
                return textField;         
    }        
} 