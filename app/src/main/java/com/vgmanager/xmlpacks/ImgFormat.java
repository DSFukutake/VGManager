package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Daniel on 5/15/2015.
 */
public class ImgFormat {
    @Element(name = "original")
    private String url;
    @Attribute(required = false)
    private int width;
    @Attribute(required = false)
    private int height;
    @Element(name = "thumb")
    private String thumbUrl;

    public String getUrl(){return thumbUrl;}
}
