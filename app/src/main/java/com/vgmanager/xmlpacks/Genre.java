package com.vgmanager.xmlpacks;

import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

/**
 * Created by Daniel on 5/15/2015.
 */
public class Genre {
    @ElementList(entry="genre",inline = true)
    public ArrayList<String> genres;

    public ArrayList<String> getGenres(){
        ArrayList<String> results = new ArrayList<>();
        if(genres != null) {
            results = genres;
        }else{
            results.add("N/A");
        }

        return results;
    }
}
