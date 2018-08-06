package com.vgmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Daniel on 5/20/2015.
 * Fragment container for overview of the game, simple scrollview with text since it can be long text.
 */
public class OverviewFragment extends Fragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle b = getArguments();
        View overview =  inflater.inflate(R.layout.overview_fragment, container, false);

        TextView overviewText  = (TextView) overview.findViewById(R.id.overview_text);
        overviewText.setText( b.getString("overview"));

        return overview;
    }

}
