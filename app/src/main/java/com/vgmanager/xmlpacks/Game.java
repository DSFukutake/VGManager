package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Element;

/**
 * Created by Daniel on 5/15/2015.
 */
public class Game {
    @Element
    private int id;
    @Element(name = "GameTitle")
    private String gameTitle;
    @Element(name = "ReleaseDate", required = false)
    private String date;
    @Element(name = "Platform", required = true)
    private String platform;

    public int getId() {return this.id;}
    public String getTitle() { return this.gameTitle;}
    public String getDate() {return this.date;}
    public String getPlatform() {return this.platform;}

    public void setId(int id) {this.id = id;}
    public void setTitle(String title) {this.gameTitle = title;}
    public void setDate(String date) {this.date = date;}
    public void setPlatform(String platform) {this.platform = platform;}
}
