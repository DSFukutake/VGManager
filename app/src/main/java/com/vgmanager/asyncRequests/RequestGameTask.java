package com.vgmanager.asyncRequests;

import android.os.AsyncTask;
import android.util.Log;

import com.vgmanager.VGManager;
import com.vgmanager.xmlpacks.GameDetail;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * Async task to request details of game with id: id
 */
public class RequestGameTask extends AsyncTask<String, Void, GameDetail> {

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
    protected void onPostExecute(GameDetail result){
        if(result != null) {
            VGManager.updateList(result.getGame());
        }
    }

}//class
