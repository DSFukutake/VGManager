package com.vgmanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.koushikdutta.ion.Ion;

/**
 * Created by Daniel on 5/20/2015.
 */
public class GameDetailsFragment extends Fragment{

    public static ToggleButton addGame;
    public static ToggleButton addWishlist;
    public static ToggleButton addBacklog;
    public static DetailsContainer gameDetails;
    public static Switch details_switch;

    public static Bundle bundle;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bundle = new Bundle();
        if(CacheGames.savedSelection == null) {
            bundle = getArguments();
            gameDetails = bundle.getParcelable("game");
            CacheGames.savedSelection = gameDetails;
            CacheGames.currentPagerSelection = 0;
        }else {
            gameDetails = CacheGames.savedSelection;
            bundle.putParcelable("game",gameDetails);
        }

        View detailView = inflater.inflate(R.layout.details_fragment_1, container, false);
        detailView.setFocusableInTouchMode(true);
        detailView.requestFocus();



        ImageView img = (ImageView)detailView.findViewById(R.id.details_cover);
        Ion.with(img).load(Config.IMG_REQUEST_BASE+gameDetails.getFrontBoxArt());

        TextView title = (TextView)detailView.findViewById(R.id.details_name);
        TextView date = (TextView)detailView.findViewById(R.id.details_release_date);
        TextView platform = (TextView)detailView.findViewById(R.id.details_platform);

        addGame = (ToggleButton)detailView.findViewById(R.id.button_add_myGames);
        addWishlist = (ToggleButton)detailView.findViewById(R.id.button_add_wishlist);
        addBacklog = (ToggleButton)detailView.findViewById(R.id.button_add_backlog);
        details_switch = (Switch)detailView.findViewById(R.id.details_switch);



        if(!TimeManager.isAvailable(gameDetails.getDate())){
            addGame.setEnabled(false);
        }else{
            addGame.setEnabled(true);
        }
        if(SaveDataManager.isInList(gameDetails.getId(), SaveDataManager.myGames)){
            addGame.setChecked(true);
            addWishlist.setEnabled(false);
            addBacklog.setEnabled(true);
            details_switch.setEnabled(true);
        }else{
            addGame.setChecked(false);
            addWishlist.setEnabled(true);
            addBacklog.setEnabled(false);
            details_switch.setEnabled(false);
        }

        if(SaveDataManager.isInList(gameDetails.getId(), SaveDataManager.wishList)){
            addWishlist.setChecked(true);
        }else{
            addWishlist.setChecked(false);
        }

        if(SaveDataManager.isInList(gameDetails.getId(), SaveDataManager.backLog)){
            addBacklog.setChecked(true);
        }else{
            addBacklog.setChecked(false);
        }


        if(SaveDataManager.isInList(gameDetails.getId(), SaveDataManager.backLog)){
            addBacklog.setChecked(true);
        }else{
            addBacklog.setChecked(false);
        }
        addGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    SaveDataManager.removeMyGame(gameDetails.getId());
                    if (!addWishlist.isEnabled()) {
                        addWishlist.setEnabled(true);
                        details_switch.setEnabled(false);
                        details_switch.setChecked(false);
                    }
                    if(addBacklog.isEnabled()){
                        addBacklog.setChecked(false);
                        addBacklog.setEnabled(false);
                    }
                } else {
                    SaveDataManager.addToMyGame(gameDetails.getId(), gameDetails.getGameTitle() + "( " + gameDetails.getPlatformName() + " )");
                    if (addWishlist.isEnabled()) {
                        addWishlist.setChecked(false);
                        addWishlist.setEnabled(false);
                        addBacklog.setEnabled(true);
                        details_switch.setEnabled(true);
                    }
                }
                SaveDataManager.saveInfo();
            }
        });

        addWishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SaveDataManager.addToWishlist(gameDetails.getId(), gameDetails.getGameTitle() + "( " + gameDetails.getPlatformName() + " )");
                } else {
                    SaveDataManager.removeFromWishlist(gameDetails.getId());
                }
                SaveDataManager.saveInfo();
            }
        });

        addBacklog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SaveDataManager.addToBacklog(gameDetails.getId(), gameDetails.getGameTitle() + "( " + gameDetails.getPlatformName() + " )");
                } else {
                    SaveDataManager.removeFromBackLog(gameDetails.getId());
                }
                SaveDataManager.saveInfo();
            }
        });

        details_switch.setChecked(false);

        Fragment pagerFragment = new PagerContainerFragment();
        pagerFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(R.id.details_fragment_switch,pagerFragment).commit();

        details_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Fragment personalFrag = new PersonalGameInfoFragment();
                    personalFrag.setArguments(bundle);
                    getChildFragmentManager().beginTransaction().replace(R.id.details_fragment_switch,personalFrag).commit();
                } else {
                    Fragment pagerFragment = new PagerContainerFragment();
                    pagerFragment.setArguments(bundle);
                    getChildFragmentManager().beginTransaction().replace(R.id.details_fragment_switch,pagerFragment).commit();
                }
            }
        });


        title.setText(gameDetails.getGameTitle());
        date.setText(gameDetails.getDate());
        platform.setText(gameDetails.getPlatformName());



        return detailView;
    }

}
