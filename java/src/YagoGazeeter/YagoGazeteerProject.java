/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yagoTypesParser;

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
       dataSet = parser.parseFileOnPath("../data/sample_yagoTypes.txt");
       for (GazeteerClass gazClass: dataSet.getClasses()){
           System.out.printf("%s\n",gazClass.getName());
       }
    }
}
