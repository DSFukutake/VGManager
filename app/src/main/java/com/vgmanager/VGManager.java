    package com.vgmanager;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.vgmanager.xmlpacks.Details;
import com.vgmanager.xmlpacks.GamesList;


import java.util.ArrayList;
import java.util.List;




/**
 * Main view class.
 * TODO: Create proper view and buttons instead of blank activity that just has search.
 */


public class VGManager extends ActionBarActivity {


    public static Context mContext;
    public static GamesList newList;
    public static List<Details> finalList;
    public static ListFragment lFrag;
    public static FragmentManager fm;
    public static ArrayAdapter<Details> adapter;
    public static VGManager mInstance;

    private static boolean isStarting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DBG", "onCreate");
        setContentView(R.layout.activity_vgmanager);
        mContext=this;
        mInstance = this;

        //Init user values
        SaveDataManager.initData();

        //Init Social stuff
        SocialWrapper.init();

        //Initialize the list fragment, the search function should be available at all times.
        lFrag = new GamesListFragment();
        fm = getSupportFragmentManager();
        finalList = new ArrayList<>();
        lFrag.setListAdapter(new BasicInfoAdapter(mContext, finalList));
        adapter = (ArrayAdapter<Details>) lFrag.getListAdapter();

        //start always on main menu
        StateMachine.init();
        isStarting = true;



    }

    @Override
    protected void onPause() {
        isStarting = false;
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("DBG", "OnStart");

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("DBG", "OnResume");

        if(!isStarting) {
            StateMachine.resumeStateMachine();
        }else{
            SaveDataManager.loadMyInfo();
            SaveDataManager.loadSettings();
            Search.taskCounter = 0;
            TimeManager.checkForReleases();
            TimeManager.checkDeadline();
        }

    }

    @Override
    public boolean onCreateOptionsMenu( final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vgmanager, menu);

        //Make search widget in the ActionBar
        final SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                try {
                    //reset search view
                    Search.savedQuery = null;
                    StateMachine.setState(StateMachine.State.STATE_SEARCH);
                    Search.cancelAllSearchTasks();
                    Search.newSearch(query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchView.clearFocus();
                //end search widget code
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            StateMachine.setState(StateMachine.State.STATE_SETTINGS);
        }
        if(id == R.id.action_main_menu){
            Search.cancelAllSearchTasks();
            StateMachine.setState(StateMachine.State.STATE_MAIN_MENU);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        StateMachine.goBack();
    }

    //Function will be called when AsyncTask for each game is finished, that way it will add to the list progressively.
    public static void updateList(Details game){
        adapter.add(game);
        adapter.notifyDataSetChanged();
    }



}
