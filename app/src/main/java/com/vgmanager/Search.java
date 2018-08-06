package com.vgmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.vgmanager.asyncRequests.RequestGameListTask;
import com.vgmanager.asyncRequests.RequestGameTask;
import com.vgmanager.asyncRequests.RequestGameTaskWithoutListUpdate;
import com.vgmanager.xmlpacks.Details;
import com.vgmanager.xmlpacks.Game;
import com.vgmanager.xmlpacks.GameDetail;

import org.hamcrest.Matcher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ch.lambdaj.function.matcher.Predicate;

import static ch.lambdaj.Lambda.filter;

/**
 * Created by Daniel on 5/28/2015.
 * Search functionality handler, as the search function can be called from different places
 * - Using search widget
 * - Returning from a detail view
 */
public class Search {


   public static List<AsyncTask> requestGameTracker;

   public static String savedQuery;
   public static ArrayList<Integer> saved;
   public static int taskCounter;


   public static void newSearch(final String query) {
       requestGameTracker = new ArrayList<>();
       try {
           VGManager.newList = new RequestGameListTask().execute( Config.QUERY_GAMELIST_URL + "\"" + query + "\"").get();
           savedQuery = query;
           if (VGManager.newList.getGame() != null) {
               VGManager.fm.beginTransaction().replace(android.R.id.content, VGManager.lFrag).commit();
               VGManager.adapter.clear();

               Collections.sort(VGManager.newList.getGame(), new Comparator<Game>() {
                   @Override
                   public int compare(Game lhs, Game rhs) {
                       return lhs.getTitle().compareTo(rhs.getTitle());
                   }
               });

               //Get the list of exactly matching names
               Matcher<Game> equalsMatcher = new Predicate<Game>() {
                   @Override
                   public boolean apply(Game game) {
                       return game.getTitle().toLowerCase().equals(query.toLowerCase());
                   }
               };
               final List<Game> exactMatches = filter(equalsMatcher, VGManager.newList.getGame());

               //Get the list of names that contain the query and that are not already in the exactMatches.
               Matcher<Game> othersMatcher = new Predicate<Game>() {
                   @Override
                   public boolean apply(Game game) {
                       return game.getTitle().toLowerCase().contains(query.toLowerCase()) && !exactMatches.contains(game);
                   }
               };
               final List<Game> otherMatches = filter(othersMatcher, VGManager.newList.getGame());

               //Do the request to get info for every relevant search item
               for (Game g : exactMatches) {
                   requestGameTracker.add(new RequestGameTask().execute(Config.QUERY_GAME_URL + g.getId()));
               }
               for (Game g : otherMatches) {
                   requestGameTracker.add(new RequestGameTask().execute(Config.QUERY_GAME_URL + g.getId()));
               }
           } else {
               Toast errorToast = Toast.makeText(VGManager.mContext, R.string.search_error, Toast.LENGTH_LONG);
               errorToast.setGravity(Gravity.CENTER, 0, 0);
               errorToast.show();
           }
       } catch (Exception e) {
            e.printStackTrace();
       }
   }

   public static void resumeSearch(final String query){
       if (VGManager.newList.getGame() != null) {

           VGManager.fm.beginTransaction().replace(android.R.id.content, VGManager.lFrag).commit();
           if(saved == null){
               saved = new ArrayList<>();
           }else{
               saved.clear();
           }
           //Get the items already in the list
           for(int i = 0; i < VGManager.adapter.getCount(); i++){
               saved.add(VGManager.adapter.getItem(i).getId());
           }

           //Get the list of exactly matching names
           Matcher<Game> equalsMatcher = new Predicate<Game>() {
               @Override
               public boolean apply(Game game) {
                   return game.getTitle().toLowerCase().equals(query.toLowerCase()) && !saved.contains(game.getId());
               }
           };
           final List<Game> exactMatches = filter(equalsMatcher, VGManager.newList.getGame());

           //Get the list of names that contain the query and that are not already in the exactMatches.
           Matcher<Game> othersMatcher = new Predicate<Game>() {
               @Override
               public boolean apply(Game game) {
                   return game.getTitle().toLowerCase().contains(query.toLowerCase()) && !exactMatches.contains(game)&& !saved.contains(game.getId());
               }
           };
           final List<Game> otherMatches = filter(othersMatcher, VGManager.newList.getGame());


           //Do the request to get info for every relevant search item
           for (Game g : exactMatches) {
                   requestGameTracker.add(new RequestGameTask().execute(Config.QUERY_GAME_URL + g.getId()));
           }
           for (Game g : otherMatches) {
                   requestGameTracker.add(new RequestGameTask().execute(Config.QUERY_GAME_URL + g.getId()));
           }
       }
   }

    public static void cancelAllSearchTasks(){
        if(requestGameTracker != null) {
            for (int i = 0; i < requestGameTracker.size(); i++) {
                requestGameTracker.get(i).cancel(true);
            }
        }
    }

    public static void loadLocalGameList(HashMap<Integer,String> idList){
        if(requestGameTracker == null) {
            requestGameTracker = new ArrayList<>();
        }

        VGManager.fm.beginTransaction().replace(android.R.id.content, VGManager.lFrag).commit();
        if(saved == null) {
            saved = new ArrayList<>();
        }else{
            saved.clear();
        }
        //Get the items already in the list
        for(int i = 0; i < VGManager.adapter.getCount(); i++){
            saved.add(VGManager.adapter.getItem(i).getId());
        }
        for (int i : idList.keySet()) {
            if(!saved.contains(i)) {
                requestGameTracker.add(new RequestGameTask().execute(Config.QUERY_GAME_URL + i));
            }
        }
    }

    public static void loadDataOnly(int id){
        if(requestGameTracker == null){
            requestGameTracker = new ArrayList<>();
        }

        requestGameTracker.add(new RequestGameTaskWithoutListUpdate().execute(Config.QUERY_GAME_URL + id));

    }

}
