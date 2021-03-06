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
import sk.sav.ui.ikt.nlp.gazetteer.character.CharacterGazetteer;
import sk.sav.ui.ikt.nlp.gazetteer.character.CharacterGazetteer.Representation;
/**
 *
 * @author Jonny999
 */
public class YagoGazeteerProject {
  /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Integer key = 1;
        GazeteerDataSet dataSet;
        HashMap<Integer, String> classMap = new HashMap<>();
        CharacterGazetteer gazetteer = new CharacterGazetteer(
				Representation.CHILDSIBLING, true);
        YagoParser parser = YagoParser.getInstance();
        
       parser.parseTypesOnPath("../data/yagoTypes.ttl");
       writeSecondTypesOutput(parser.getGazeteerDataSet());
        
        parser.parseAltNamesOnPath("../data/yagoMultilingualInstanceLabels.ttl");
        dataSet = parser.getGazeteerDataSet();
        //writeAltNamesOuput(dataSet);
        
        //Gazeteer test.
        String input = "The pace in 2014 was vastly different. This year's highlights were so above and beyond that without any discussion, we each picked seven of the same releases. Though I'm not going to divulge which ones they were, there was such a bounty of phenomenal albums released this year that the selection process was a joy. From John Dowland's elegant, elegiac and intimate pavanes, written at the dawn of the 17th century, to John (Coolidge) Adams' Saxophone Concerto and John Luther Adams' Become Ocean, both premiered just last year, the scope of what we loved reaches far and wide.";
        for(GazeteerClass item : dataSet.getClasses()){
            classMap.put(key, item.getName());
            for(String name : item.getItems()){
                gazetteer.insert(name, key);
            }
            key++;
        }
      List<int[]> matches = gazetteer.find(input);
      for (int[] match : matches) {
			String ids = "[";
			for (int i = 2; i < match.length; i++) {
				ids += classMap.get(match[i])+ ", ";
                                
			}
			ids += "]";
			System.out.println(String.format("[%02d,%02d] %s -> %s", match[0],
					match[1], input.substring(match[0], match[1]), ids));
		}

      System.out.printf("Class count: %d\n",dataSet.getClassesCount());
      System.out.printf("Item count: %d\n",dataSet.getYagoNameCount());
    }
    private static void writeFirstTypesOutput(GazeteerDataSet dataSet){
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
    
   private  static void writeSecondTypesOutput(GazeteerDataSet dataSet){
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
   
    private  static void writeAltNamesOuput(GazeteerDataSet dataSet){
       try {
            try (BufferedWriter out = new BufferedWriter(new FileWriter("../data/yagoAltNamesOutput.txt"))) {
                HashMap<String,ArrayList<String>> altNameMap = dataSet.getAltNamesMap();
                ArrayList<String> altNames;
                
                for(String name : altNameMap.keySet()){
                    out.write(name+" - ");
                    altNames = altNameMap.get(name);
                    for(String title : altNames){
                        out.write("["+title+"]" + " ");
                    }
                    out.write("\n");
                }
            }
        } catch (IOException e) {}
   }
}
