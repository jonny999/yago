/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import yagoTypesParser.*;

/**
 *
 * @author janhandzus
 */
public class ParserTest {
    
    public ParserTest() {
    }
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
     @Test
     public void ParserTest() {
         YagoParser parser = YagoParser.getInstance();
         List<GazeteerClass> classes;
         GazeteerClass myGazClass;
         GazeteerDataSet dataSet = parser.parseTypesOnPath("../data/sample_yagoTypes.txt");
         
         Assert.assertTrue(dataSet.getClassesCount()==36 && dataSet.getYagoNameCount()== 42);
         
         classes = dataSet.getClasses();
         myGazClass = classes.get(0);
         
         Assert.assertEquals("presidents of indonesia", myGazClass.getName());
        
     }
}
