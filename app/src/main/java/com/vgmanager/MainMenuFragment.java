package com.vgmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.share.widget.ShareButton;


/**
 * Created by Daniel on 6/8/2015.
 */
public class MainMenuFragment extends Fragment{

    protected static Button myGamesButton;
    protected static Button wishlistButton;
    protected static Button backlogButton;

    protected static Button shareOwnedButton;
    protected static Button backLogDeadlineButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainMenu = inflater.inflate(R.layout.main_menu_fragment, container, false);
        mainMenu.setFocusableInTouchMode(true);
        mainMenu.requestFocus();
        myGamesButton = (Button) mainMenu.findViewById(R.id.button_myGames);
        wishlistButton = (Button) mainMenu.findViewById(R.id.button_wishlist);
        backlogButton = (Button) mainMenu.findViewById(R.id.button_backlog);

        shareOwnedButton = (Button) mainMenu.findViewById(R.id.button_main_share_owned);
        backLogDeadlineButton = (Button) mainMenu.findViewById(R.id.button_main_deadline);


        myGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateMachine.setState(StateMachine.State.STATE_MY_GAMES);
            }
        });
        wishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateMachine.setState(StateMachine.State.STATE_WISHLIST);
            }
        });
        backlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateMachine.setState(StateMachine.State.STATE_BACKLOG);
            }
        });


        shareOwnedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(VGManager.mContext);
                LayoutInflater layoutInflater = (LayoutInflater) VGManager.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View dialogView = layoutInflater.inflate(R.layout.share_dialog_layout, null);
                dialog.setView(dialogView);
                ShareButton fbShareButton = (ShareButton) dialogView.findViewById(R.id.share_dialog_facebook_button);
                final StringBuilder listOfGames = new StringBuilder();
                for(Integer i: SaveDataManager.myGames.keySet()){
                    listOfGames.append(SaveDataManager.myGames.get(i) + "\n");
                }
                fbShareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SocialWrapper.share(SocialWrapper.socialNetworkId.SOCIAL_NETWORK_ID_FACEBOOK,
                                listOfGames.toString(),
                                "Check out my collection of owned games!",
                                "http://shadowdragonknight.sss-fc.com/wp-content/uploads/2015/01/DOD3.jpg");
                    }
                });

                Button twitterShareButton = (Button) dialogView.findViewById(R.id.share_dialog_twitter_button);
                twitterShareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SocialWrapper.share(SocialWrapper.socialNetworkId.SOCIAL_NETWORK_ID_TWITTER,
                                listOfGames.toString(),
                                "Check out my collection of owned games!",
                                "");
                    }
                });


                dialog.setTitle(VGManager.mInstance.getResources().getString(R.string.share_dialog_title));
                dialog.setPositiveButton(VGManager.mInstance.getResources().getString(R.string.share_dialog_button_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.create().show();
            }
        });

        backLogDeadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeManager.displayAddBacklogDeadline();
            }
        });

        return mainMenu;
    }

}
