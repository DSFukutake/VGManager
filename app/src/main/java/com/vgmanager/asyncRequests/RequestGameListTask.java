package com.vgmanager.asyncRequests;

import android.os.AsyncTask;
import android.util.Log;

import com.vgmanager.xmlpacks.GamesList;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * AsyncTask to request game list with format id/name/platform
 */
public class RequestGameListTask extends AsyncTask<String, String, GamesList> {

    @Override
    protected GamesList doInBackground(String... params){
        try {
            final String url = params[0];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());
            GamesList data = restTemplate.getForObject(url, GamesList.class);
            return data;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }


}//class
