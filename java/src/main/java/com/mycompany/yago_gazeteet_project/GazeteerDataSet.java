/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.yago_gazeteet_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Jonny999
 */
public class GazeteerDataSet {
    private  List<GazeteerClass> classes;
    private  HashMap<String, Integer> classMap;
    private static int itemCount = -1;
  //Constructors. 
    
    public GazeteerDataSet(){
        classMap = new HashMap<>();
        classes = new ArrayList<>();
    }
    
  //Public methods.
    
    public void addGazeteerItem(String className, String itemName){
     GazeteerClass currClass = this.getGazeteerClass(className);
        if (null == currClass) {
            Integer key;
            int count = classes.size();
            
            if(count == 0 ){
                count = 1;
            }
            
            currClass = new GazeteerClass(className);
            
            key = count-1;
            classMap.put(className,key); 
            classes.add(currClass);
        }
       
        currClass.addItem(itemName);
    }
    
    public GazeteerClass getGazeteerClass(String className){
        Integer index =  classMap.get(className);
        
        if(index == null){
            return null;
        }
        
        return this.classes.get(index);
    }

    public List<GazeteerClass> getClasses() {
        return classes;
    }
    
    public Set<String> getAllClassNames(){
        return this.classMap.keySet();
    }
    
    public int getClassesCount(){
        return classes.size();
    }
    
    public int getItemCount(){
       
       if(itemCount == -1){
           itemCount = 0;
           for (GazeteerClass item : this.classes) {
               itemCount += item.getItems().size();
           }
       }
       
       return this.itemCount;
    }
}
