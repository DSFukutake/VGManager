package com.vgmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;

/**
 * Created by Daniel on 6/15/2015.
 */
public class PersonalInfoAdapter extends ArrayAdapter<String>{
    public static Context mContext;
    public static ArrayList<String> contents;
    private static int game;
    public static int selection;

    public PersonalInfoAdapter(Context context,ArrayList<String> values,int gameId) {
        super(context,R.layout.personal_info_list_row, values);
        mContext = context;
        contents = values;
        game = gameId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.personal_info_list_row,null);
            holder = new ViewHolder();
            holder.imageChecked = (ImageView) convertView.findViewById(R.id.selection_completed);
            holder.text = (TextView)convertView.findViewById(R.id.selection_content);
            holder.completeButton = (Button)convertView.findViewById(R.id.selection_button);
            holder.removeButton = (Button)convertView.findViewById(R.id.selection_remove_goals);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        //populate the element of the list.

        holder.text.setText(SaveDataManager.parseHashString(contents.get(position)));

        if(getComplete(contents.get(position))){
            holder.imageChecked.setVisibility(View.VISIBLE);
            holder.completeButton.setVisibility(View.GONE);
        }else{
            holder.imageChecked.setVisibility(View.GONE);
            holder.completeButton.setVisibility(View.VISIBLE);
        }

        if(selection == R.id.radio_scores || selection == R.id.radio_timers){
            holder.completeButton.setVisibility(View.GONE);
        }else{
            holder.completeButton.setVisibility(View.VISIBLE);
        }


        holder.completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDataManager.addComplete(contents.get(position));
                notifyDataSetChanged();
                SaveDataManager.saveInfo();

                AlertDialog.Builder dialog = new AlertDialog.Builder(VGManager.mContext);
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View dialogView = layoutInflater.inflate(R.layout.share_dialog_layout, null);
                dialog.setView(dialogView);
                ShareButton fbShareButton = (ShareButton) dialogView.findViewById(R.id.share_dialog_facebook_button);
                fbShareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SocialWrapper.share(SocialWrapper.socialNetworkId.SOCIAL_NETWORK_ID_FACEBOOK,
                                VGManager.mInstance.getResources().getString(SocialWrapper.getShareString(selection)) + "\n" + SaveDataManager.parseHashString(contents.get(position)) + " in " + CacheGames.savedSelection.getGameTitle(),
                                VGManager.mInstance.getResources().getString(R.string.share_title),
                                Config.IMG_REQUEST_BASE + CacheGames.savedSelection.getFrontBoxArtThumb());
                    }
                });

                Button twitterShareButton = (Button) dialogView.findViewById(R.id.share_dialog_twitter_button);
                twitterShareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SocialWrapper.share(SocialWrapper.socialNetworkId.SOCIAL_NETWORK_ID_TWITTER,
                                VGManager.mInstance.getResources().getString(SocialWrapper.getShareString(selection)) + "\n" + SaveDataManager.parseHashString(contents.get(position)) + " in " + CacheGames.savedSelection.getGameTitle(),
                                VGManager.mInstance.getResources().getString(R.string.share_title),
                                Config.IMG_REQUEST_BASE + CacheGames.savedSelection.getFrontBoxArtThumb());
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


        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (selection){
                    case R.id.radio_goals:
                        SaveDataManager.removeRecord(game, position, Config.HASH_KEY_GOALS);
                        break;
                    case R.id.radio_challenges:
                        SaveDataManager.removeRecord(game, position, Config.HASH_KEY_CHALLENGE);
                        break;
                    case R.id.radio_scores:
                        SaveDataManager.removeRecord(game, position, Config.HASH_KEY_SCORE);
                        break;
                    case R.id.radio_timers:
                        SaveDataManager.removeRecord(game, position, Config.HASH_KEY_TIMER);
                        break;
                }
                contents.remove(position);
                notifyDataSetChanged();
                SaveDataManager.saveInfo();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView imageChecked;
        TextView text;
        Button completeButton;
        Button removeButton;
    }

    private static boolean getComplete(String in){
        return SaveDataManager.recordsCompletition.get(SaveDataManager.getStringId(in));
    }
}
