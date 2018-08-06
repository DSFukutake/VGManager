package com.vgmanager;

import  android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vgmanager.xmlpacks.Details;

/**
 * Listfragment for displaying the list returned by the search function.
 */
public class GamesListFragment extends ListFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View listView = inflater.inflate(R.layout.gameslist_list_fragment, container, false);
        return listView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Search.cancelAllSearchTasks();
        Fragment fragment = new GameDetailsFragment();
        DetailsContainer container = new DetailsContainer((Details)l.getItemAtPosition(position));
        Bundle gameInfo = new Bundle();
        gameInfo.putParcelable("game",container);
        fragment.setArguments(gameInfo);
        StateMachine.setState(StateMachine.State.STATE_DETAILS);
        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }

}