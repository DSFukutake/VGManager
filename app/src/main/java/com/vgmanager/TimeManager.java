package com.vgmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniel on 6/9/2015.
 * Class to handle and manipulate any time related operation
 */
public class TimeManager {

    public static HashMap<Integer,String> releases;
    protected static EditText deadlineText;
    protected static long daysToDeadline;

    public static boolean isAvailable(String release){
        boolean result;
        Calendar currentDate = Calendar.getInstance();

        try {
            Date releaseDate = new SimpleDateFormat("MM/dd/yyyy").parse(release);
            result =  currentDate.getTime().after(releaseDate);
        }catch(Exception e){
            result = true;
        }
        return result;
    }

    public static void checkForReleases(){
        releases = new HashMap<>();
        if(!SaveDataManager.wishList.isEmpty()){
            for(int i: SaveDataManager.wishList.keySet()){
               Search.loadDataOnly(i);
            }
        }else{
            return;
        }
    }

    public static void checkDeadline(){
        daysToDeadline = 0;
        Calendar currentDate = Calendar.getInstance();
        try{
            Date currentDeadline = new SimpleDateFormat("MM/dd/yyyy").parse(SaveDataManager.getBacklogDeadline());
            boolean expired = currentDate.getTime().after(currentDeadline);
            daysToDeadline =getDateDiff(currentDate.getTime(),currentDeadline,TimeUnit.DAYS);
            if(SaveDataManager.backLog.size() > 0 && expired){
                displayDeadlineReachedDialog();
            }else if(SaveDataManager.backLog.size() > 0 && !expired && daysToDeadline <= 5 ){
                displayDeadlineCloseDialog();
            }
        }catch(Exception e){

        }
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit){
        long diffInMillis = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillis,TimeUnit.MILLISECONDS);
    }

    public static void displayReleaseDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(VGManager.mContext);
        dialog.setTitle("These games on your wishlist released!");

        ScrollView dialogScroll =  new ScrollView(VGManager.mContext);
        TextView dialogText = new TextView(VGManager.mContext);
        dialogText.setText("");

       for(int i: releases.keySet()){
            dialogText.append(releases.get(i) + "\n");
        }

        dialogScroll.addView(dialogText);
        dialog.setView(dialogScroll);

        dialog.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void displayAddBacklogDeadline(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(VGManager.mContext);
        dialog.setTitle("Set a deadline for your backlog");

        LinearLayout dialogLayout = new LinearLayout(VGManager.mContext);
        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        deadlineText = new EditText(VGManager.mContext);
        CheckBox notificationsCheckbox = new CheckBox(VGManager.mContext);
        notificationsCheckbox.setChecked(false);

        dialogLayout.addView(deadlineText);
        dialogLayout.addView(notificationsCheckbox);

        dialog.setView(dialogLayout);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveDataManager.setBackLogDeadline(deadlineText.getText().toString());
                dialog.dismiss();
                SaveDataManager.saveInfo();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void displayDeadlineCloseDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(VGManager.mContext);
        dialog.setTitle("Deadline is close!");
        dialog.setMessage("Your backlog deadline is in " + (daysToDeadline+1) + "days.");
        dialog.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void displayDeadlineReachedDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(VGManager.mContext);
        dialog.setTitle("Deadline breached");
        dialog.setMessage("Oh no! Looks like you missed your backlog deadline");
        dialog.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
