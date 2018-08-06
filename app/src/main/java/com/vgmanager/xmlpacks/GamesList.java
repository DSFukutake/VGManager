package com.vgmanager.xmlpacks;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Daniel on 5/14/2015.
 * REST object containing a list of <Game> </Game> xml elements from GetGamesList.php query
 */
@Root(name="Data")
public class GamesList {
    @ElementList(entry="Game",inline = true,required = false)
    private List<Game> game;

    public List<Game> getGame(){
        return game;
    }
    public boolean searchSuccess(){
        if(game != null){
            return true;
        }
        return false;
    }

}
