package com.bjut.search.field;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public class SingleTitleField{        
    private static TextField textField = null;        
    private SingleTitleField(){   }        
    public static TextField getInstance(){        
        if (textField == null)        
        	textField = new TextField("title","title" , Field.Store.NO);         
                return textField;         
    }        
} 