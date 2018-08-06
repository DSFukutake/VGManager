package com.vgmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


/**
 * Created by Daniel on 6/10/2015.
 */
public class SettingsFragment extends Fragment {

    protected static Button saveSettingsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View settingsView =  inflater.inflate(R.layout.settings_layout, container, false);
        settingsView.setFocusableInTouchMode(true);
        settingsView.requestFocus();

        saveSettingsButton = (Button)settingsView.findViewById(R.id.settings_save_button);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDataManager.saveSettings();
            }
        });


        return settingsView;
    }
}
