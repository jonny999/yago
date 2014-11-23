/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yagoTypesParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
/**
 *
 * @author Jonny999
 */
public class GazeteerDataSet {
    private  List<GazeteerClass> yagoClasses;
    private  HashMap<String, Integer> classMap;
    private  HashMap<String, Integer> titleMap;
    
    private  int itemCount;
  //Constructors. 
    
    public GazeteerDataSet(){
        this.classMap = new HashMap<>();
        this.titleMap = new HashMap<>();
        this.yagoClasses = new ArrayList<>();
        this.itemCount = -1;
    }
    
  //Public methods.
    
    public void addGazeteerItem(String className, String itemName){
     GazeteerClass currClass = this.getGazeteerClass(className);
        if (null == currClass) {
            Integer key;
            int count = this.yagoClasses.size();
            
            if(count == 0 ){
                count = 1;
            }
            
            currClass = new GazeteerClass(className);
            
            key = count-1;
            this.classMap.put(className,key); 
            this.yagoClasses.add(currClass);
        }
       
        currClass.addItem(itemName);
    }
    
    public GazeteerClass getGazeteerClass(String className){
        Integer index =  this.classMap.get(className);
        if (index != null) {
            return this.yagoClasses.get(index);
        }
        return null;
    }
    public Integer getTitleId(String title){
        Integer id = this.titleMap.get(title);
        
        return id;
    }
    public List<GazeteerClass> getClasses() {
        return this.yagoClasses;
    }
    
    public Set<String> getAllClassNames(){
        return this.classMap.keySet();
    }
    
    public int getClassesCount(){
        return this.yagoClasses.size();
    }
    
    public int getYagoClassesCount(){
       
       if(this.itemCount == -1){
           this.itemCount = 0;
           for (GazeteerClass item : this.yagoClasses) {
               this.itemCount += item.getItems().size();
           }
       }
       
       return this.itemCount;
    }
}
