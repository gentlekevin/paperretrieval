package com.bjut.search.field;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public class SingleAuthorField{        
    private static TextField textField = null;        
    private SingleAuthorField(){   }        
    public static TextField getInstance(){        
        if (textField == null)        
        	textField = new TextField("author","author" , Field.Store.NO);         
                return textField;         
    }        
} 