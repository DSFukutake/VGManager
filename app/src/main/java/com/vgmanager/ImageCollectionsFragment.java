package com.vgmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.ion.Ion;

import java.util.Collections;

/**
 * Created by Daniel on 5/22/2015.
 * Fragment containing the youtube video, screenshots, fanart and boxart sections.
 */
public class ImageCollectionsFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle b = getArguments();
        View collections =  inflater.inflate(R.layout.images_collection_fragment, container, false);
        String url = b.getString("trailer");
        //youtube
        if(url != null) {
            if (url != "") {
                url = url.split("=")[1];
            }

            YouTubeVideoFragment videofrag = YouTubeVideoFragment.newInstance(url);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.videofragment, videofrag).commit();
        }else{
            collections.findViewById(R.id.videofragment).setVisibility(View.GONE);
        }
        //Fanart
        if(b.containsKey("fanart")) {
            LinearLayout fanartGrid = (LinearLayout) collections.findViewById(R.id.collections_fanart_grid);
            for(String s: b.getStringArrayList("fanart")){
                fanartGrid.addView(getImageView(s));
            }
        }else{
            collections.findViewById(R.id.collections_fanart_grid).setVisibility(View.GONE);
            collections.findViewById(R.id.collections_fanart_text).setVisibility(View.GONE);
        }

        //Boxart
        if(b.containsKey("boxart")) {
            LinearLayout boxArtGrid = (LinearLayout) collections.findViewById(R.id.collections_boxart_grid);
            Collections.reverse(b.getStringArrayList("boxart"));
            for(String s: b.getStringArrayList("boxart")){
                boxArtGrid.addView(getImageView(s));
            }
        }else{
            collections.findViewById(R.id.collections_boxart_grid).setVisibility(View.GONE);
            collections.findViewById(R.id.collections_boxart_text).setVisibility(View.GONE);
        }

        //Screenshots
        if(b.containsKey("screens")) {
            LinearLayout screenshotsGrid = (LinearLayout) collections.findViewById(R.id.collections_screenshots_grid);
            for(String s: b.getStringArrayList("screens")){
                screenshotsGrid.addView(getImageView(s));
            }
        }else{
            collections.findViewById(R.id.collections_screenshots_grid).setVisibility(View.GONE);
            collections.findViewById(R.id.collections_screens_text).setVisibility(View.GONE);
        }


        return collections;
    }

    private View getImageView(String url){
        ImageView imageView  = new ImageView(VGManager.mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 10, 0);
        imageView.setLayoutParams(lp);
        Ion.with(imageView).load(Config.IMG_REQUEST_BASE + url);
        return imageView;
    }

}
