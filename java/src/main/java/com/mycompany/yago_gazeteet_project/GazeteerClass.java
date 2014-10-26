/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.yago_gazeteet_project;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonny999
 */
public class GazeteerClass {

    private String name;
    private List<String> items;

    //Constructors.

    public GazeteerClass(String name) {
        this.items = new ArrayList<>();
        this.name = "N/A";
        if (name != null && name.length() > 0) {
            this.name = name;
        }
    }

    public GazeteerClass() {
        this.items = new ArrayList<>();
        this.name = "N/A";
    }

    //Public Methods.

    public String getName() {
        return name;
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String itemName) {
        this.items.add(itemName);
    }
}
