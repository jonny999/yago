/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yagoTypesParser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author Jonny999
 */
public class YagoGazeteerProject {
  /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       GazeteerDataSet dataSet;
        YagoParser parser = YagoParser.getInstance();
       dataSet = parser.parseTypesOnPath("../data/sample_yagoTypes.txt");
        writeSecondOutput(dataSet);
      
    }
    private static void writeFirstOutput(GazeteerDataSet dataSet){
        try {
            try (BufferedWriter out = new BufferedWriter(new FileWriter("../data/yagoTypesOutput1.txt"))) {
                for (GazeteerClass gazClass: dataSet.getClasses()) {
                    out.write("Class: " + gazClass.getName() + "\n");
                    for (String title : gazClass.getItems()) {
                        out.write("\t\t: " + title + "\n");
                    }
                }
            }
        } catch (IOException e) {}
   }
   private  static void writeSecondOutput(GazeteerDataSet dataSet){
       try {
            try (BufferedWriter out = new BufferedWriter(new FileWriter("../data/yagoTypesOutput2.txt"))) {
                HashMap<String,ArrayList<String>> nameMap = dataSet.getNameMap();
                ArrayList<String> classTitles;
                
                for(String name : nameMap.keySet()){
                    out.write(name+" - ");
                    classTitles = nameMap.get(name);
                    for(String title : classTitles){
                        out.write("["+title+"]" + " ");
                    }
                    out.write("\n");
                }
            }
        } catch (IOException e) {}
   }
}
