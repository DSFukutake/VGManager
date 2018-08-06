package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Daniel on 5/15/2015.
 * REST object for <banner> </banner> xml element.
 */
public class Banner {
    @Element(name = "banner",required = false)
    String bannerImg;
    @Attribute(required = false)
    private int width;
    @Attribute(required = false)
    private int height;


}
