/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.mycompany.yago_gazeteet_project.GazeteerClass;
import com.mycompany.yago_gazeteet_project.GazeteerDataSet;
import com.mycompany.yago_gazeteet_project.YAGOParser;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Jonny999
 */
public class SampleTest {
    
    public SampleTest() {
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {
         YAGOParser parser = YAGOParser.getInstance();
         List<GazeteerClass> classes;
         GazeteerClass myGazClass;
         parser.processNewFile("../data/sample_yagoTypes.txt");
         GazeteerDataSet dataSet = parser.getGazeteerDataSet();
         
         Assert.assertTrue(dataSet.getClassesCount()==36 && dataSet.getItemCount() == 42);
         
         classes = dataSet.getClasses();
         myGazClass = classes.get(0);
         Assert.assertEquals("Manchu people", myGazClass.getName());
        
     }
}
