package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Element;

import java.util.ArrayList;

/**
 * Created by Daniel on 5/15/2015.
 * REST Object containing all the <Game> </Game> detailed information.
 */
public class Details {
    @Element
    private int id;
    @Element(name = "GameTitle")
    private String gameTitle;
    @Element(name = "AlternateTitles",required = false)
    private Title alternate;
    @Element(name = "PlatformId")
    private int platformId;
    @Element(name = "Platform")
    private String platformName;
    @Element(name = "ReleaseDate", required = false)
    private String date;
    @Element(name = "Overview", required = false)
    private String synopsis;
    @Element(name = "ESRB", required = false)
    private String ratingESRB;
    @Element(name = "Genres", required = false)
    private Genre genres;
    @Element(name="Players",required = false)
    private String numPlayers;
    @Element(name="Co-op", required = false)
    private String co_op;
    @Element(name="Youtube", required = false)
    private String trailerUrl;
    @Element(name="Publisher", required = false)
    private String publisher;
    @Element(name="Developer", required = false)
    private String developer;
    @Element(name="Actors",required = false)
    private String actors;
    @Element(name="Rating", required = false)
    private float criticRating;
    @Element(name = "Similar", required = false)
    private Similar similarInList;
    @Element(name="Images",required = false)
    private XMLImages images;

    public int getId(){return id;}
    public String getGameTitle(){return gameTitle;}
    public ArrayList<String> getTitles(){
        if(alternate != null){
            return alternate.getTitles();
        }
        return null;
    }
    public String getPlatformName(){return platformName;}
    public String getDate(){return date;}
    public String getSynopsis() {
        if (synopsis != null) {
            return synopsis;
        }
        return "N/A";
    }
    public String getRatingESRB() {return ratingESRB;}
    public ArrayList<String> getGenres(){
        ArrayList<String> genInfo = new ArrayList<>();
        if(genres != null){
            genInfo = genres.getGenres();
        }else{
            genInfo.add("N/A");
        }
        return genInfo;
    }
    public String getNumPlayers() {return numPlayers;}
    public String getCo_op() {return co_op;}
    public String getTrailerUrl() {return trailerUrl;}
    public String getPublisher() {return publisher;}
    public String getDeveloper() {return developer;}
    public float getCriticRating() {return criticRating;}
    public ArrayList<String> getFanart(){return images.getFanartURLs();}
    public ArrayList<String> getBoxart(){return images.getAllBoxArtURLs();}
    public ArrayList<String> getScreenshots(){return images.getScreenshotURLs();}
    public String getBoxArtUrl(){
        return images.getFrontBoxArtURL();
    }
    public String getBoxArtThumb() {return images.getFrontBoxArtThumbURL();}

}
