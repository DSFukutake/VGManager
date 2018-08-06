package com.vgmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Daniel on 6/13/2015.
 */
public class PagerContainerFragment extends Fragment {
    private static PagerAdapter mPagerAdapter;
    private static ViewPager mPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View pagerContainerView = inflater.inflate(R.layout.detailed_pager_layout, container, false);
        FragmentManager fm = getFragmentManager();
        Bundle bundle = getArguments();
        mPager = (ViewPager)pagerContainerView.findViewById(R.id.details_pager);
        mPagerAdapter = new CustomFragmentPagerAdapter(fm,bundle);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(CacheGames.currentPagerSelection);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                CacheGames.currentPagerSelection = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return pagerContainerView;
    }

}
