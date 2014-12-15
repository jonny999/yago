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
    
    private  HashMap<String, ArrayList<String>> nameClassesMap;
    private  HashMap<String, ArrayList<String>> altNamesMap;
     
    private  int itemCount;
    
   
  //Constructors. 
    
    public GazeteerDataSet(){
        this.classMap       = new HashMap<>();
        this.titleMap       = new HashMap<>();
        this.itemCount      = -1;
        
        this.nameClassesMap = new HashMap<>();
        this.altNamesMap    = new HashMap<>();
    }
    
  //Public methods.
    
    public void addGazeteerItem(String className, String itemName){    
     GazeteerClass currClass;
     className =  this.getMainCategorie(className);
     ArrayList<String> nameClasses;
        if (className !=null) {
            currClass = this.classMap.get(className);
            if (null == currClass) {
                currClass = new GazeteerClass(className);

                this.classMap.put(className, currClass);
            }

            nameClasses = this.getNameClasses(itemName);
            
            if(this.contentString(nameClasses, className) == false){
                nameClasses.add(className);
            }
            
            currClass.addItem(itemName);
        }
    }
    
    private ArrayList getNameClasses(String name){
        ArrayList<String> result = this.nameClassesMap.get(name);
        
        if(result == null){
            result =  new ArrayList<>();
            this.nameClassesMap.put(name,result);
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
        return nameClassesMap;
    }
    public List<GazeteerClass> getClasses(){
            List<GazeteerClass> classes  = new ArrayList<>();
            for(String key : this.classMap.keySet()){
                classes.add(this.classMap.get(key));
            }
            return classes;
    }
    public void addAltName(String name, String altName){
        ArrayList<String> altNames;
        if(name.equalsIgnoreCase(altName) == false){
            altNames = altNamesMap.get(name);
            if(altNames == null){
                altNames = new ArrayList<>();
                altNamesMap.put(name, altNames);
            }
            
            if(this.contentString(altNames, altName)==false){
                 altNames.add(altName);
            }
        }
    }

    public HashMap<String, ArrayList<String>> getAltNamesMap() {
        return altNamesMap;
    }
    
    private String getMainCategorie(String className){
         String[] categories = {"people", "rulers", "presidents",
         "united states","actors","musicians","albums","players","films","books"};
        
        for(String item : categories){
            if(className.toLowerCase().contains(item))
                return item;
        }
        return null;
        
    }
    
    private boolean contentString(ArrayList<String> array, String string){
        for(String item: array){
            if(item.equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
}
