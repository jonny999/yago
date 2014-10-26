/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.yago_gazeteet_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonny999
 */
public class YAGOParser {

    private YAGOParser() {
    }

    public static YAGOParser getInstance() {
        return YAGOParserHolder.INSTANCE;
    }

    private static class YAGOParserHolder {

        private static final YAGOParser INSTANCE = new YAGOParser();
    }
    private GazeteerDataSet gazeteerDataSet;

//Public methods.
    public void processNewFile(String path) {
        gazeteerDataSet = new GazeteerDataSet();

        BufferedReader input = this.openYAGOFile(path);
        this.processFile(input);
    }

    public GazeteerDataSet getGazeteerDataSet() {
        return gazeteerDataSet;
    }

//Private methods
    //Open file.
    private BufferedReader openYAGOFile(String path) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(
                    new FileReader(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(YAGOParser.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(YAGOParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Process one line and add one item to dataset

    private void processLine(String lineStr) {
        String[] parts = lineStr.split(">.*<");

        if (parts.length == 2) {

            String className = this.clearString(parts[1]);
            String itemName = this.clearString(parts[0]);

            this.gazeteerDataSet.addGazeteerItem(className, itemName);

        }
    }

    //Clear String
    
    private String clearString(String part) {
        String finalStr = part.replaceAll("[<>. ]|wikicategory_", "");
        finalStr = finalStr.replaceAll("_", " ");

        if (finalStr.length() == 0) {
            finalStr = "N/A";
        }

        return finalStr;
    }
}
