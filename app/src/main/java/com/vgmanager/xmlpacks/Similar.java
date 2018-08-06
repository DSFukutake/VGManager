package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by Daniel on 5/15/2015.
 */
public class Similar {
    @Element(name = "SimilarCount")
    private int count;
    @ElementList(entry = "Game",inline = true)
    public List<SimpleGame> similarGames;
}
