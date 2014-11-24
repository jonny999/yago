/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yagoTypesParser;
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Jonny999
 */
public class YagoParser {
    private GazeteerDataSet gazeteerDataSet;
     private YagoParser() {
    }

    public static YagoParser getInstance() {
        return YAGOParserHolder.INSTANCE;
    }

    private static class YAGOParserHolder {

        private static final YagoParser INSTANCE = new YagoParser();
    }

//Public methods.
    public GazeteerDataSet parseTypesOnPath(String path) {
        this.gazeteerDataSet = new GazeteerDataSet();

        BufferedReader input = this.openYAGOFile(path);
        this.processFile(input);
        
       return this.gazeteerDataSet;
    }

//Private methods
    //Open file.
    private BufferedReader openYAGOFile(String path) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(
                    new FileReader(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(YagoParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return input;
    }

    //Read and parse File.

    private void processFile(BufferedReader input) {
        String lineStr;

        try {
            lineStr = input.readLine();
            while (lineStr != null) {
                //Throw away useless lines.
                if ((lineStr.length() > 0) && ('<' == lineStr.charAt(0))) {
                    this.processLine(lineStr);
                }
                lineStr = input.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(YagoParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Process one line and add one item to dataset

    private void processLine(String lineStr) {
        String[] parts = lineStr.split(">.*<");

        if (parts.length == 2) {

            String className = this.cleanString(parts[1]);
            String itemName = this.cleanString(parts[0]);

            this.gazeteerDataSet.addGazeteerItem(className,itemName);

        }
    }

    //Clean String
    
    private String cleanString(String part) {
        
        Pattern pattern;
        pattern = Pattern.compile("<(.*)>.rdfs:label.*\"(.*)\"");
        Matcher matcher = pattern.matcher(part);

        if (matcher.find()) {
            matcher.group(1);
            matcher.group(2);
        }
        
        
        String finalStr = part.replaceAll("[<>. ]|^[a-zA-Z0-9]*_", "");
        finalStr = finalStr.replaceAll("_", " ");
        if (finalStr.length() == 0) {
            finalStr = "N/A";
        }

        return finalStr.toLowerCase();
    }   
}
