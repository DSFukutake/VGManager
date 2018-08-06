package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Daniel on 5/15/2015.
 */
public class ClearLogo {

    @Attribute(required = false)
    private int width;
    @Attribute(required = false)
    private int height;
    @Element(name="clearlogo",required=false)
    private String clearlogo;
}
