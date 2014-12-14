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
    private  HashMap<String, GazeteerClass> classMap;
    private  HashMap<String, Integer> titleMap;
    
    private  HashMap<String, ArrayList<String>> nameMap;
    
    private  int itemCount;
  //Constructors. 
    
    public GazeteerDataSet(){
        this.classMap    = new HashMap<>();
        this.titleMap    = new HashMap<>();
        this.itemCount   = -1;
        
        this.nameMap     = new HashMap<>();
    }
    
  //Public methods.
    
    public void addGazeteerItem(String className, String itemName){
     GazeteerClass currClass = this.classMap.get(className);
        if (null == currClass) {            
            currClass = new GazeteerClass(className);
            
            this.classMap.put(className,currClass); 
            this.getNameClasses(itemName).add(className);
        }
       
        currClass.addItem(itemName);
    }
    
    private ArrayList getNameClasses(String name){
        ArrayList<String> result = this.nameMap.get(name);
        
        if(result == null){
            result =  new ArrayList<>();
            this.nameMap.put(name,result);
        }
        
        return result;
    }
    public Integer getTitleId(String title){
        Integer id = this.titleMap.get(title);
        
        return id;
    }
    
    public Set<String> getAllClassTitles(){
        return this.classMap.keySet();
    }
    
    public int getClassesCount(){
        return this.classMap.size();
    }
    
    public int getYagoNameCount(){
       
       if(this.itemCount == -1){
           GazeteerClass item;
           this.itemCount = 0;
           for(String key : this.classMap.keySet()){
               item = classMap.get(key);
               this.itemCount += item.getItems().size();
           }
       }
       
       return this.itemCount;
    }

    public HashMap<String, ArrayList<String>> getNameMap() {
        return nameMap;
    }
    public List<GazeteerClass> getClasses(){
            List<GazeteerClass> classes  = new ArrayList<>();
            for(String key : this.classMap.keySet()){
                classes.add(this.classMap.get(key));
            }
            return classes;
    }
}
