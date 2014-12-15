/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import sk.sav.ui.ikt.nlp.gazetteer.character.CharacterGazetteer;
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
        GazeteerDataSet dataSet;

        parser.parseTypesOnPath("../data/sample_yagoTypes.txt");
        dataSet = parser.getGazeteerDataSet();

        Assert.assertTrue(dataSet.getClassesCount() == 4 && dataSet.getYagoNameCount() == 20);

        classes = dataSet.getClasses();
        myGazClass = classes.get(0);

        Assert.assertEquals("rulers", myGazClass.getName());

    }

    @Test
    public void GazeteerTest() {
        Integer key = 1;
        YagoParser parser = YagoParser.getInstance();
        GazeteerDataSet dataSet;
        HashMap<Integer, String> classMap = new HashMap<>();
        CharacterGazetteer gazetteer = new CharacterGazetteer(
                CharacterGazetteer.Representation.CHILDSIBLING, true);

        parser.parseTypesOnPath("../data/sample_yagoTypes.txt");
        dataSet = parser.getGazeteerDataSet();

        String input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                + " Mauris eu Kid Rock diam. Maecenas a egestas elit, ac condimentum lacus."
                + " Integer in pretium nisi. Curabitur sollicitudin tellus id mi accumsan,"
                + " quis finibus velit vehicula. Sed id urna auctor, fermentum leo sit amet,"
                + " dignissim sem. Fusce dapibus ante vel sem pellentesque fermentum."
                + " Proin nec augue ante. Cum sociis Michael Foot et magnis dis parturient montes,"
                + " nascetur ridiculus mus.  laoreet quis augue ac mattis."
                + " Nunc interdum, nunc eu ultricies sollicitudin, neque enim fermentum neque."
                + " in Susilo Bambang Yudhoyono orci gravida diam. ";

        for (GazeteerClass item : dataSet.getClasses()) {
            classMap.put(key, item.getName());
            for (String name : item.getItems()) {
                gazetteer.insert(name, key);
            }
            key++;
        }
        List<int[]> matches = gazetteer.find(input);

        int[] match = matches.get(0);
        String id = classMap.get(match[2]);
        
        Assert.assertEquals("musicians", id);
        Assert.assertEquals("Kid Rock", input.substring(match[0], match[1]));

    }
}
