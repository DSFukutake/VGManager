package com.vgmanager.asyncRequests;

import android.os.AsyncTask;
import android.util.Log;

import com.vgmanager.DetailsContainer;
import com.vgmanager.Search;
import com.vgmanager.TimeManager;
import com.vgmanager.VGManager;
import com.vgmanager.xmlpacks.GameDetail;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * Async task to request details of game with id: id
 */
public class RequestGameTaskWithoutListUpdate extends AsyncTask<String, Void, GameDetail> {

    @Override
    protected GameDetail doInBackground(String... params){
        try {
            final String url = params[0];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());
            GameDetail data = restTemplate.getForObject(url,GameDetail.class);
            return data;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(GameDetail result) {
        if(result != null){
            TimeManager.releases.put(result.getGame().getId(), result.getGame().getGameTitle());
        }
        Search.taskCounter++;
        if(Search.taskCounter == Search.requestGameTracker.size()){
            TimeManager.displayReleaseDialog();
        }
    }
}//class
