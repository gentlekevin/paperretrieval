package com.bjut.search.field;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class SingleSourceField{        
    private static StringField textField = null;        
    private SingleSourceField(){   }        
    public static StringField getInstance(){        
        if (textField == null)        
        	textField = new StringField("source","source" , Field.Store.NO);         
                return textField;         
    }        
} 