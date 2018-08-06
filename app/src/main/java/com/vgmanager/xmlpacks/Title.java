package com.vgmanager.xmlpacks;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

/**
 * Created by Daniel on 5/15/2015.
 */
public class Title {
    @ElementList(entry = "title",inline = true,required = false)
    private ArrayList<String> titles;

    public ArrayList<String> getTitles(){return this.titles;}
}
