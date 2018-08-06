package com.vgmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by Daniel on 6/13/2015.
 */
public class PersonalGameInfoFragment extends Fragment{


    private static DetailsContainer game;
    private static ArrayList<String> displayList;
    private static EditText editTextAdd;
    private static PersonalInfoAdapter displayAdapter;
    private static int currentSelection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View personalGameInfoView = inflater.inflate(R.layout.personal_game_info, container, false);

        Bundle bundle = getArguments();
        game  = bundle.getParcelable("game");

        final Button addButton = (Button) personalGameInfoView.findViewById(R.id.personal_add_button);

        final ListView displayView = (ListView) personalGameInfoView.findViewById(R.id.personal_list);

        displayList = new ArrayList<>();
        RadioGroup radioGroup = (RadioGroup) personalGameInfoView.findViewById(R.id.personal_radio_group);
        currentSelection = R.id.radio_goals;
        displayAdapter = new PersonalInfoAdapter(VGManager.mContext,displayList,game.getId());
        displayView.setAdapter(displayAdapter);

        //Initial fill
        if (SaveDataManager.personalRecords.containsKey(game.getId()) && SaveDataManager.personalRecords.get(game.getId()).containsKey(Config.HASH_KEY_GOALS)) {
            for (String g : SaveDataManager.personalRecords.get(game.getId()).get(Config.HASH_KEY_GOALS)) {
                displayAdapter.add(g);
                displayAdapter.notifyDataSetChanged();
            }
        }


        editTextAdd = (EditText) personalGameInfoView.findViewById(R.id.personal_editText);
        editTextAdd.setBackgroundColor(Color.WHITE);
        editTextAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Nothing to do
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().matches(Config.REGEX_NON_EMPTY)){
                    addButton.setEnabled(true);
                }else{
                    addButton.setEnabled(false);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                displayAdapter.clear();
                displayAdapter.selection = checkedId;
                currentSelection = checkedId;

                switch (checkedId) {
                    case R.id.radio_goals:
                        if (SaveDataManager.personalRecords.containsKey(game.getId()) && SaveDataManager.personalRecords.get(game.getId()).containsKey(Config.HASH_KEY_GOALS)) {
                            for (String g : SaveDataManager.personalRecords.get(game.getId()).get(Config.HASH_KEY_GOALS)) {
                                displayAdapter.add(g);
                                displayAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    case R.id.radio_challenges:
                        if (SaveDataManager.personalRecords.containsKey(game.getId())  && SaveDataManager.personalRecords.get(game.getId()).containsKey(Config.HASH_KEY_CHALLENGE)) {
                            for (String g : SaveDataManager.personalRecords.get(game.getId()).get(Config.HASH_KEY_CHALLENGE)) {
                                displayAdapter.add(g);
                                displayAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    case R.id.radio_scores:
                        if (SaveDataManager.personalRecords.containsKey(game.getId()) && SaveDataManager.personalRecords.get(game.getId()).containsKey(Config.HASH_KEY_SCORE)) {
                            for (String g : SaveDataManager.personalRecords.get(game.getId()).get(Config.HASH_KEY_SCORE)) {
                                displayAdapter.add(g);
                                displayAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    case R.id.radio_timers:
                        if (SaveDataManager.personalRecords.containsKey(game.getId()) && SaveDataManager.personalRecords.get(game.getId()).containsKey(Config.HASH_KEY_TIMER)) {
                            for (String g : SaveDataManager.personalRecords.get(game.getId()).get(Config.HASH_KEY_TIMER)) {
                                displayAdapter.add(g);
                                displayAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
            }
        });

        addButton.setEnabled(false);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAdd.clearFocus();
                addButton.setEnabled(false);
                switch (currentSelection) {
                    case R.id.radio_goals:
                        SaveDataManager.addRecord(game.getId(), editTextAdd.getText().toString(), Config.HASH_KEY_GOALS);
                        break;
                    case R.id.radio_challenges:
                        SaveDataManager.addRecord(game.getId(), editTextAdd.getText().toString(), Config.HASH_KEY_CHALLENGE);
                        break;
                    case R.id.radio_scores:
                        SaveDataManager.addRecord(game.getId(), editTextAdd.getText().toString(), Config.HASH_KEY_SCORE);
                        break;
                    case R.id.radio_timers:
                        SaveDataManager.addRecord(game.getId(), editTextAdd.getText().toString(), Config.HASH_KEY_TIMER);
                        break;
                }
                displayAdapter.add(editTextAdd.getText().toString() + SaveDataManager.stringIdAppend());
                displayAdapter.notifyDataSetChanged();

                editTextAdd.setText("");
                SaveDataManager.saveInfo();
            }
        });


        return personalGameInfoView;
    }


}
