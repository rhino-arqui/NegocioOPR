/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 *
 * @author root
 */
public class ExclusionStrategyOnClass implements ExclusionStrategy{

    private Class<?> _default;
    private int count;

 
    public ExclusionStrategyOnClass() {
        this._default = null;
        this.count = 0;
    }

    
    
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes fa) {
        if( fa.getAnnotations().contains("ManyToMany") || fa.getAnnotations().contains("OneToMany") )
        {
            return true;
        }
        return false;
    }

    /**
      * Custom field exclusion goes here
      */
    /*
    public boolean shouldSkipField(FieldAttributes f) {
        
    }
    */

}
