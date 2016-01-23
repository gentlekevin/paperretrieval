package com.bjut.search.field;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;

public class SingleIdField{        
    private static LongField longField = null;        
    private SingleIdField(){   }        
    public static LongField getInstance(){        
        if (longField == null)        
        	longField = new LongField("id",0L , Field.Store.YES);         
                return longField;         
    }        
} 