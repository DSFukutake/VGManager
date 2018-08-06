package com.vgmanager;

import java.io.File;

/**
 * Created by Daniel on 5/25/2015.
 */
public class Config {

    public static final String GOOGLE_DEVELOPER_KEY = "AIzaSyArZhDxkbP3VYxTLBop1DGqf7IuSlH36Wg";



    public static final String TWITTER_KEY = "2zW8jSaTZLt3VI11o8IybPv7a";
    public static final String TWITTER_SECRET = "K79D1mPx32ja3cSxZVjokr6M81BnKbcI4opF1PYpyFDhjkrR22";

    public static final String QUERY_GAMELIST_URL="http://thegamesdb.net/api/GetGamesList.php?name=";
    public static final String QUERY_GAME_URL="http://thegamesdb.net/api/GetGame.php?id=";
    public static final String IMG_REQUEST_BASE = "http://thegamesdb.net/banners/";

    public static final String INTERNAL_STORAGE_PATH  = VGManager.mContext.getFilesDir()+ File.separator;
    public static final String SAVE_FILE_NAME = "myInfo.json";
    public static final String SETTINGS_FILE_NAME = "settings.json";

    public static final String HASH_KEY_GOALS = "goals";
    public static final String HASH_KEY_CHALLENGE = "challenges";
    public static final String HASH_KEY_SCORE = "scores";
    public static final String HASH_KEY_TIMER = "timers";

    public static final String REGEX_NON_EMPTY = ".*\\S.*";

}
