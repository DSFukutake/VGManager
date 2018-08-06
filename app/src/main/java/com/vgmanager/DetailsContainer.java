package com.vgmanager;

import android.os.Parcel;
import android.os.Parcelable;

import com.vgmanager.xmlpacks.Details;

import java.util.ArrayList;

/**
 * Created by Daniel on 5/20/2015.
 * Container for Details Objects since they are REST objects, this will be used to pass data around.
 */
public class DetailsContainer implements Parcelable{
    private int id;
    private String gameTitle;
    private ArrayList<String> alternate;
    private String platformName;
    private String date;
    private String synopsis;
    private String ratingESRB;
    private ArrayList<String> genres;
    private String numPlayers;
    private String co_op;
    private String trailerUrl;
    private String publisher;
    private String developer;
    private float criticRating;
    private ArrayList<String> boxart;
    private ArrayList<String> fanart;
    private ArrayList<String> screenshots;
    private String frontBoxArt;
    private String frontBoxArtThumb;

    public DetailsContainer(Details game){
        this.id = game.getId();
        this.gameTitle = game.getGameTitle();
        this.alternate = game.getTitles();
        this.platformName = game.getPlatformName();
        this.date = game.getDate();
        this.synopsis = game.getSynopsis();
        this.ratingESRB = game.getRatingESRB();
        this.genres = game.getGenres();
        this.numPlayers = game.getNumPlayers();
        this.co_op = game.getCo_op();
        this.trailerUrl = game.getTrailerUrl();
        this.publisher = game.getPublisher();
        this.developer = game.getDeveloper();
        this.criticRating = game.getCriticRating();
        this.boxart = game.getBoxart();
        this.fanart = game.getFanart();
        this.screenshots = game.getScreenshots();
        this.frontBoxArt = game.getBoxArtUrl();
        this.frontBoxArtThumb = game.getBoxArtThumb();

    }

    public int getId() {
        return id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public ArrayList<String> getAlternate() {
        return alternate;
    }

    public String getPlatformName() {
        return platformName;
    }

    public String getDate() {
        return date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getRatingESRB() {
        return ratingESRB;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getNumPlayers() {
        return numPlayers;
    }

    public String getCo_op() {
        return co_op;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public float getCriticRating() {
        return criticRating;
    }

    public ArrayList<String> getBoxart() {
        return boxart;
    }

    public ArrayList<String> getFanart() {
        return fanart;
    }

    public ArrayList<String> getScreenshots() {
        return screenshots;
    }

    public String getFrontBoxArt() {
        return frontBoxArt;
    }

    public String getFrontBoxArtThumb(){return frontBoxArtThumb;}

    public static final Parcelable.Creator<DetailsContainer> CREATOR
            = new Parcelable.Creator<DetailsContainer>() {
        public DetailsContainer createFromParcel(Parcel in) {
            return new DetailsContainer(in);
        }
        public DetailsContainer[] newArray(int size) {
            return new DetailsContainer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(gameTitle);
        dest.writeStringList(alternate);
        dest.writeString(platformName);
        dest.writeString(date);
        dest.writeString(synopsis);
        dest.writeString(ratingESRB);
        dest.writeStringList(genres);
        dest.writeString(numPlayers);
        dest.writeString(co_op);
        dest.writeString(trailerUrl);
        dest.writeString(publisher);
        dest.writeString(developer);
        dest.writeFloat(criticRating);
        dest.writeStringList(boxart);
        dest.writeStringList(fanart);
        dest.writeStringList(screenshots);
        dest.writeString(frontBoxArt);
        dest.writeString(frontBoxArtThumb);
    }

    public DetailsContainer(Parcel in){
        id = in.readInt();
        gameTitle = in.readString();
        in.readStringList(alternate);
        platformName = in.readString();
        date = in.readString();
        synopsis = in.readString();
        ratingESRB = in.readString();
        in.readStringList(genres);
        numPlayers = in.readString();
        co_op = in.readString();
        trailerUrl = in.readString();
        publisher = in.readString();
        developer = in.readString();
        criticRating = in.readFloat();
        in.readStringList(boxart);
        in.readStringList(fanart);
        in.readStringList(screenshots);
        frontBoxArt = in.readString();
        frontBoxArtThumb = in.readString();
    }
}
