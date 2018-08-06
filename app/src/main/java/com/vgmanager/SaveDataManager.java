package com.vgmanager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Daniel on 6/8/2015.
 * SaveDataManager class contains all the user lists , will handle I/O and management of the app.
 */
public class SaveDataManager {


    public static HashMap<Integer,String> myGames;
    public static HashMap<Integer,String> wishList;
    public static HashMap<Integer,String> backLog;


    public static HashMap<Integer, HashMap<String,ArrayList<String>>> personalRecords;
    public static long stringId;
    public static HashMap<Long, Boolean> recordsCompletition;

    public static boolean facebookShareEnabled;
    public static boolean twitterShareEnabled;
    public static boolean wishlistNotificationEnabled;
    public static boolean backlogNotificationEnabled;

    private static String backlogDeadline;


    public static void initData(){

        myGames = new HashMap<>();
        wishList = new HashMap<>();
        backLog = new HashMap<>();

        personalRecords = new HashMap<>();
        recordsCompletition = new HashMap<>();
        stringId = 0;

        facebookShareEnabled = true;
        twitterShareEnabled = true;
        wishlistNotificationEnabled = true;
        backlogNotificationEnabled = true;

        backlogDeadline = "";
    }

    public static void addToMyGame(int id,String name){
        add(id,name, myGames);
        removeFromWishlist(id);
    }

    public static void removeMyGame(int id){
        remove(id, myGames);
        if(personalRecords.containsKey(id)){
            personalRecords.remove(id);
        }
    }

    public static void addToWishlist(int id, String name){
        add(id, name, wishList);
    }

    public static void removeFromWishlist(int id){
        remove(id, wishList);
    }

    public static void addToBacklog(int id,String name){
        add(id, name, backLog);
    }

    public static void removeFromBackLog(int id){
        remove(id, backLog);
    }

    public static boolean isInList(int id, HashMap<Integer,String> list){
        if(list.containsKey(id)){
            return true;
        }
        return false;
    }

    public static void addRecord(int id, String record, String targetList){

        record += ":stringId:"+stringId;
        recordsCompletition.put(stringId,false);
        stringId++;

        if(personalRecords.get(id) != null ) {
            HashMap<String, ArrayList<String>> oldRecord = personalRecords.get(id);
            if(oldRecord.get(targetList) != null ) {
                oldRecord.get(targetList).add(record);
            }else{
                ArrayList<String> newRecordList = new ArrayList<>();
                newRecordList.add(record);
                oldRecord.put(targetList,newRecordList);
            }
            personalRecords.put(id,oldRecord);
        }else{
            HashMap<String, ArrayList<String>> newRecord = new HashMap<>();
            ArrayList<String> newRecordList = new ArrayList<>();
            newRecordList.add(record);
            newRecord.put(targetList, newRecordList);
            personalRecords.put(id, newRecord);
        }


    }

    public static void addComplete(String record){
        String[] split = record.split(":stringId:");
        long recordId = Long.parseLong(split[1]);
        recordsCompletition.put(recordId, true);
    }

    public static void removeRecord(int id,int index, String targetList){
        ArrayList<String> temp = personalRecords.get(id).get(targetList);

        recordsCompletition.remove(getStringId(temp.get(index)));
        temp.remove(index);
        if(temp.size() <= 0){
            personalRecords.get(id).remove(targetList);
            if(personalRecords.get(id).isEmpty()){
                personalRecords.remove(id);
            }
        }else {
            personalRecords.get(id).put(targetList, temp);
        }
    }



    private static void remove(int id,HashMap<Integer,String> targetList){
        targetList.remove(id);
    }

    private static void add(int id,String name, HashMap<Integer,String> targetList){
        targetList.put(id, name);
    }


    public static void setBackLogDeadline(String date){
        backlogDeadline = date;
    }

    public static String getBacklogDeadline(){
        return backlogDeadline;
    }

    public static void loadMyInfo(){
        //load from memory saved lists I/O operations go here
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(Config.INTERNAL_STORAGE_PATH + Config.SAVE_FILE_NAME)));
            String read;
            StringBuilder builder = new StringBuilder("");
            while((read = bufferedReader.readLine()) != null){
                builder.append(read);
            }

            myGames.clear();
            wishList.clear();
            backLog.clear();
            personalRecords.clear();

            JSONObject obj = new JSONObject(builder.toString());

            if(obj.has("MyGames")) {
                JSONArray myJSONGames = obj.getJSONArray("MyGames");
                JSONArray myJSONGamesNames = obj.getJSONArray("MyGamesNames");
                for (int i = 0; i < myJSONGames.length(); i++) {
                    int id = myJSONGames.getInt(i);
                    String name = myJSONGamesNames.getString(i);
                    addToMyGame(id,name);
                }
            }
            if(obj.has("Wishlist")) {
                JSONArray myJSONWishlist = obj.getJSONArray("Wishlist");
                JSONArray myJSONWishlistNames = obj.getJSONArray("WhislistNames");
                for (int i = 0; i < myJSONWishlist.length(); i++) {
                    int id = myJSONWishlist.getInt(i);
                    String name = myJSONWishlistNames.getString(i);
                    addToWishlist(id,name);
                }
            }
            if(obj.has("Backlog")) {
                JSONArray myJSONBacklog = obj.getJSONArray("Backlog");
                JSONArray myJSONBacklogNames = obj.getJSONArray("BacklogNames");
                for (int i = 0; i < myJSONBacklog.length(); i++) {
                    int id = myJSONBacklog.getInt(i);
                    String name = myJSONBacklogNames.getString(i);
                    addToBacklog(id,name);
                }
            }

            JSONObject personalObject = obj.getJSONObject("Personal");
            Iterator<String> jsonKeys =  personalObject.keys();
            while(jsonKeys.hasNext()){
                String intKey = jsonKeys.next();
                int gameId = Integer.parseInt(intKey);
                JSONObject record = personalObject.getJSONObject(intKey);
                Iterator<String> innerKey = record.keys();
                HashMap<String,ArrayList<String>> recordToPut = new HashMap<>();
                while(innerKey.hasNext()){

                    String recordType = innerKey.next();

                    JSONArray records = record.getJSONArray(recordType);
                    ArrayList<String> stringsToPut = new ArrayList<>();
                    for(int i = 0; i < records.length();i++){
                        stringsToPut.add(records.getString(i));
                    }

                    recordToPut.put(recordType,stringsToPut);
                }

                personalRecords.put(gameId,recordToPut);
            }

            JSONObject completition = obj.getJSONObject("CompletitionTrack");
            Iterator<String> complKeys = completition.keys();
            while(complKeys.hasNext()){
                String key = complKeys.next();
                long idKey = Long.parseLong(key);
                recordsCompletition.put(idKey,completition.getBoolean(key));
            }

            backlogDeadline = obj.getString("BacklogDeadline");
            stringId  = obj.getLong("StringIdTrack");

            bufferedReader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveInfo(){
        //save to memory saved lists I/O operations go here
        try {

            JSONObject obj = new JSONObject();

            if (!myGames.isEmpty()) {
                JSONArray myGamesJSON = new JSONArray();
                JSONArray myGamesNamesJSON = new JSONArray();
                for (int i : myGames.keySet()) {
                    myGamesJSON.put(i);
                    myGamesNamesJSON.put(myGames.get(i));
                }
                obj.put("MyGames", myGamesJSON);
                obj.put("MyGamesNames",myGamesNamesJSON);
            }

            if(!wishList.isEmpty()){
                JSONArray myWishlistJSON = new JSONArray();
                JSONArray myWishlistNamesJSON = new JSONArray();
                for (int i : wishList.keySet()) {
                    myWishlistJSON.put(i);
                    myWishlistNamesJSON.put(wishList.get(i));
                }
                obj.put("Wishlist", myWishlistJSON);
                obj.put("WhislistNames",myWishlistNamesJSON);
            }

            if(!backLog.isEmpty()){
                JSONArray myBacklogJSON = new JSONArray();
                JSONArray myBacklogNamesJSON = new JSONArray();
                for (int i : backLog.keySet()) {
                    myBacklogJSON.put(i);
                    myBacklogNamesJSON.put(backLog.get(i));
                }
                obj.put("Backlog", myBacklogJSON);
                obj.put("BacklogNames",myBacklogNamesJSON);
            }

            JSONObject personal = new JSONObject();
            for(int i: personalRecords.keySet()){
                JSONObject recordType = new JSONObject();
                for(String s: personalRecords.get(i).keySet()){
                    JSONArray recordData = new JSONArray();
                    for(String s2: personalRecords.get(i).get(s)){
                        recordData.put(s2);
                    }
                    recordType.put(s,recordData);
                }
                personal.put(""+i,recordType);
            }
            obj.put("Personal", personal);

            JSONObject completition = new JSONObject();
            for(long i: recordsCompletition.keySet()){
                completition.put(""+i,recordsCompletition.get(i));
            }
            obj.put("CompletitionTrack",completition);

            obj.put("BacklogDeadline",backlogDeadline);
            obj.put("StringIdTrack",stringId);


            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Config.INTERNAL_STORAGE_PATH + Config.SAVE_FILE_NAME));
            bufferedWriter.write(obj.toString());

            bufferedWriter.flush();
            bufferedWriter.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void saveSettings(){
        try {

            JSONObject obj = new JSONObject();
            obj.put("FacebookShare", facebookShareEnabled);
            obj.put("TwitterShare",twitterShareEnabled);
            obj.put("WishlistNotification",wishlistNotificationEnabled);
            obj.put("BacklogNotification",backlogNotificationEnabled);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Config.INTERNAL_STORAGE_PATH + Config.SETTINGS_FILE_NAME));
            bufferedWriter.write(obj.toString());

            bufferedWriter.flush();
            bufferedWriter.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void loadSettings(){
        //load from memory saved lists I/O operations go here
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(Config.INTERNAL_STORAGE_PATH + Config.SETTINGS_FILE_NAME)));
            String read;
            StringBuilder builder = new StringBuilder("");
            while((read = bufferedReader.readLine()) != null){
                builder.append(read);
            }

            JSONObject obj = new JSONObject(builder.toString());
            facebookShareEnabled = obj.getBoolean("FacebookShare");
            twitterShareEnabled = obj.getBoolean("TwitterShare");
            wishlistNotificationEnabled = obj.getBoolean("WishlistNotification");
            backlogNotificationEnabled = obj.getBoolean("BacklogNotification");

            bufferedReader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String parseHashString(String in){
        String[] split = in.split(":stringId:");
        return split[0];
    }

    public static long getStringId(String in){
        String[] split = in.split(":stringId:");
        return Long.parseLong(split[1]);
    }

    public static String stringIdAppend(){
        return ":stringId:" + (stringId-1);
    }

}
