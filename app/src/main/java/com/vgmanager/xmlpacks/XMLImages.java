package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 5/15/2015.
 */
public class XMLImages {

    @ElementList(entry = "fanart",inline = true,required = false)
    private List<ImgFormat> fanArt;
    @ElementList(entry="boxart",inline = true,required = false)
    private List<BoxArt> boxart;
    @ElementList(name = "banner",inline = true,required = false)
    private List<Banner> bannerList;
    @ElementList(entry = "screenshot",inline = true,required = false)
    private List<ImgFormat> screenshots;
    @Element(name="clearlogo",required=false)
    private ClearLogo clearlogo;

    public String getFrontBoxArtURL(){
        if(boxart != null) {
            for (BoxArt b : boxart) {
                if (b.getSide().equals("front")) {
                    return b.getUrl();
                }
            }
        }
        return "";
    }

    public String getFrontBoxArtThumbURL(){
        if(boxart != null) {
            for (BoxArt b : boxart) {
                if (b.getSide().equals("front")) {
                    return b.getThumb();
                }
            }
        }
        return "";
    }

    public ArrayList<String> getFanartURLs(){
        ArrayList<String> urls = new ArrayList<>();
        if(fanArt != null) {
            for (ImgFormat img : fanArt) {
                urls.add(img.getUrl());
            }
        }
        return urls;
    }

    public ArrayList<String> getAllBoxArtURLs(){
        ArrayList<String> urls = new ArrayList<>();
        if(boxart != null) {
            for (BoxArt b : boxart) {
                urls.add(b.getThumb());
            }
        }
        return urls;
    }

    public ArrayList<String> getScreenshotURLs(){
        ArrayList<String> urls = new ArrayList<>();
        if(screenshots != null) {
            for (ImgFormat img : screenshots) {
                urls.add(img.getUrl());
            }
        }
        return urls;
    }



}
