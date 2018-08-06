package com.vgmanager.xmlpacks;

import org.simpleframework.xml.Element;

/**
 * Created by Daniel on 5/15/2015.
 */
public class SimpleGame {
    @Element
    private int id;
    @Element(name = "PlatformId")
    private int platform;
}
