package com.vgmanager;

import android.net.Uri;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Daniel on 6/17/2015.
 */
public class SocialWrapper {

    public static ShareDialog shareDialog;
    public enum socialNetworkId{
        SOCIAL_NETWORK_ID_FACEBOOK,
        SOCIAL_NETWORK_ID_TWITTER,
    }


    public static void init(){
        //Initialize Facebook sdk
        FacebookSdk.sdkInitialize(VGManager.mContext);
        //Initialize Twitter sdk
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Config.TWITTER_KEY,Config.TWITTER_SECRET);
        Fabric.with(VGManager.mContext,new Twitter(authConfig));
        Fabric.with(VGManager.mContext,new TweetComposer());

    }

    public static void share(socialNetworkId socialNetowrk, String shareMsg, String title, String image){
        switch (socialNetowrk){
            case SOCIAL_NETWORK_ID_FACEBOOK:
                shareFacebook(shareMsg, title, image);
                break;
            case SOCIAL_NETWORK_ID_TWITTER:
                shareTwitter(shareMsg,title,image);
                break;
            default:
                break;
        }
    }


    public static void shareFacebook(String message, String title, String image){
        shareDialog = new ShareDialog(VGManager.mInstance);
        ShareLinkContent linkContent = new ShareLinkContent.Builder().setContentTitle(title)
                .setContentDescription(message)
                .setImageUrl(Uri.parse(image)).build();
        shareDialog.show(linkContent);
    }

    public static void shareTwitter(String message,String title,String image){
        TweetComposer.Builder builder = new TweetComposer.Builder(VGManager.mContext).text(title + "\n" + message).image(Uri.parse(image));
        builder.show();
    }

    public static int getShareString(int selection){
        int result = R.string.share_goal;
        switch (selection){
            case R.id.radio_goals:
                result = R.string.share_goal;
                break;
            case R.id.radio_challenges:
                result = R.string.share_challenge;
                break;
            case R.id.radio_scores:
                result = R.string.share_score;
                break;
            case R.id.radio_timers:
                result = R.string.share_timer;
                break;
        }
        return result;
    }




}
