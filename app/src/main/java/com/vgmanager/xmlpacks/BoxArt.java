package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Created by Daniel on 5/15/2015.
 * REST object to contain <boxart> </boxart> xml element.
 */
public class BoxArt {
    @Attribute
    private String side;
    @Attribute(required = false)
    private int width;
    @Attribute(required = false)
    private int  height;
    @Attribute
    private String thumb;
    @Text(required = false)
    private String url;

    public String getSide(){return side;}
    public String getUrl(){return url;}
    public String getThumb(){return thumb;}
    public int getBoxartWidth(){return width;}
    public int getBoxartHeight(){return height;}
}
