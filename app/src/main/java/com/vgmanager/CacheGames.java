package com.vgmanager;

/**
 * Created by Daniel on 6/11/2015.
 */
public class CacheGames {
    public static DetailsContainer savedSelection;
    public static int currentPagerSelection;

    public static void clear(){
        savedSelection = null;
        currentPagerSelection = 0;

    }
}
