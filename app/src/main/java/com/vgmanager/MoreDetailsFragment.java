package com.vgmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Daniel on 5/21/2015.
 */
public class MoreDetailsFragment extends Fragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle b = getArguments();
        View moreInfo =  inflater.inflate(R.layout.more_details_fragment, container, false);

        TextView esrbText  = (TextView) moreInfo.findViewById(R.id.more_details_esrb);
        esrbText.setText( "ESRB: " + b.getString("esrb"));
        TextView rating  = (TextView) moreInfo.findViewById(R.id.more_details_rating);
        rating.setText( "User rating: " + b.getFloat("rating"));
        TextView developer  = (TextView) moreInfo.findViewById(R.id.more_details_developer);
        developer.setText( "Developer: " + b.getString("developer"));
        TextView publisher  = (TextView) moreInfo.findViewById(R.id.more_details_publisher);
        publisher.setText( "Publisher: " + b.getString("publisher"));
        TextView players  = (TextView) moreInfo.findViewById(R.id.more_details_players);
        players.setText( "# of players: " + b.getString("players"));
        TextView co_op  = (TextView) moreInfo.findViewById(R.id.more_details_coop);
        co_op.setText( "CO-OP: " + b.getString("co_op"));

        return moreInfo;
    }
}
