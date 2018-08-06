package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Created by Daniel on 5/14/2015.
 * REST object to contain the <Game> </Game> xml element from getGame.php query
 */
@Root(name="Data")
public class GameDetail {

    @Element(name="baseImgUrl")
    private String baseImgUrl;

    @Element(name = "Game")
    private Details game;

    public Details getGame(){return this.game;}
    public String getBaseImgUrl(){return baseImgUrl;}

}

