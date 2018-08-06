package com.vgmanager;

import android.util.Log;

import java.util.Stack;

/**
 * Created by Daniel on 6/10/2015.
 * State machine to handle indexing of pages and proper transition handling if needed
 */
public class StateMachine {

    public enum State{
        STATE_MAIN_MENU,    //0
        STATE_SEARCH,       //1
        STATE_DETAILS,      //2
        STATE_MY_GAMES,     //3
        STATE_WISHLIST,     //4
        STATE_BACKLOG,      //5
        STATE_SETTINGS,     //6
        STATE_NUM_STATES    //7
    }


    private static Stack<State> stateStack;
    private static State previousState;

    public static void init(){
        stateStack = new Stack<>();
        stateStack.push(State.STATE_MAIN_MENU);
        transitionTo(stateStack.peek());
    }

    public static void clear(){
        stateStack.clear();
        stateStack.push(State.STATE_MAIN_MENU);
    }

    public static void setState(State state){
        previousState = stateStack.peek();
        stateStack.push(state);
        transitionTo(state);
    }

    public static void goBack(){
        Search.cancelAllSearchTasks();
        if(stateStack.peek() == State.STATE_MAIN_MENU){
            //TODO:  closing app code here
        }else {
            stateStack.pop();
            transitionTo(stateStack.peek());
        }
    }

    public static void resumeStateMachine(){
        Log.e("DBFG", "Stack size: " + stateStack.size() + " Stack state: " + stateStack.peek());
        transitionTo(stateStack.peek());
    }

    private static void transitionTo(State to){
        switch (to){
            case STATE_SEARCH:
                CacheGames.clear();
                if(Search.savedQuery != null) {
                    Search.resumeSearch(Search.savedQuery);
                }
                break;
            case STATE_DETAILS:
                if(!isListState(previousState)){
                    VGManager.fm.beginTransaction().replace(android.R.id.content,new GameDetailsFragment()).commit();
                }
                break;
            case STATE_MAIN_MENU:
                CacheGames.clear();
                clear();
                VGManager.adapter.clear();
                VGManager.adapter.notifyDataSetChanged();
                VGManager.fm.beginTransaction().replace(android.R.id.content, new MainMenuFragment()).commit();
                break;
            case STATE_MY_GAMES:
                CacheGames.clear();
                Search.cancelAllSearchTasks();
                Search.loadLocalGameList(SaveDataManager.myGames);
                break;
            case STATE_WISHLIST:
                CacheGames.clear();
                Search.cancelAllSearchTasks();
                Search.loadLocalGameList(SaveDataManager.wishList);
                break;
            case STATE_BACKLOG:
                CacheGames.clear();
                Search.cancelAllSearchTasks();
                Search.loadLocalGameList(SaveDataManager.backLog);
                break;
            case STATE_SETTINGS:
                Search.cancelAllSearchTasks();
                VGManager.fm.beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
                break;
            default:
                break;
        }

    }

    private static boolean isListState(State state){
        boolean result;
        switch (state){
            case STATE_SEARCH:
            case STATE_WISHLIST:
            case STATE_BACKLOG:
            case STATE_MY_GAMES:
                result = true;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }
}
