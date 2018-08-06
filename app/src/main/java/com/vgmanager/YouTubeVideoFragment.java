package com.vgmanager;

import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by Daniel on 5/22/2015.
 * Fragment used to display and play youtube videos.
 */
public class YouTubeVideoFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    private static String youTubeId;

    public YouTubeVideoFragment(){}

    public static YouTubeVideoFragment newInstance(String id){
        YouTubeVideoFragment video = new YouTubeVideoFragment();
        youTubeId = id;
        video.init();

        return video;

    }

    private void init(){
        initialize(Config.GOOGLE_DEVELOPER_KEY,this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            youTubePlayer.cueVideo(youTubeId);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(getActivity(), 1).show();
        } else {
            String errorMessage = youTubeInitializationResult.toString();
            Toast.makeText(VGManager.mContext,errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
