package com.vgmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vgmanager.placeholder.PlaceHolderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 5/21/2015.
 */
public class CustomFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private final DetailsContainer info;

    public CustomFragmentPagerAdapter(FragmentManager fm,Bundle bundle) {
        super(fm);
        info = bundle.getParcelable("game");

    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            Fragment fragment = new OverviewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("overview",info.getSynopsis());
            fragment.setArguments(bundle);
            return fragment;
        }

        if(position == 1){
            Fragment fragment = new MoreDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("esrb", info.getRatingESRB());
            bundle.putFloat("rating", info.getCriticRating());
            bundle.putString("developer", info.getDeveloper());
            bundle.putString("publisher", info.getPublisher());
            bundle.putString("players", info.getNumPlayers());
            bundle.putString("co_op",info.getCo_op());
            fragment.setArguments(bundle);
            return fragment;
        }

        if(position == 2){
            Fragment fragment = new ImageCollectionsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("trailer",info.getTrailerUrl());
            if(!info.getFanart().isEmpty()) {
                bundle.putStringArrayList("fanart", info.getFanart());
            }
            if(!info.getBoxart().isEmpty()) {
                bundle.putStringArrayList("boxart", info.getBoxart());
            }
            if(!info.getScreenshots().isEmpty()){
                bundle.putStringArrayList("screens",info.getScreenshots());
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        return new PlaceHolderFragment();

    }

    @Override
    public int getCount() {

        return 3;
    }


}
