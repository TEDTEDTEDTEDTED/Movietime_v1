package com.example.jingjing.blogv6;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Newsfragment extends Fragment{

    @Nullable
    private android.support.design.widget.TabLayout mTabs;
    private ViewPager mViewPager;
    private RelativeLayout  new1;
    private RelativeLayout new2;
    private RelativeLayout new3;





    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news,null);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Here is where we can get the context.
        Log.e("jjjj", "onAttach called.");
        new1 = new new1(context);
        new2 = new new2(context);
        new3 = new new3(context);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Log.e("jjjj", String.valueOf(getView()));
        mTabs = (android.support.design.widget.TabLayout) getView().findViewById(R.id.toptab_news);
        mViewPager = (ViewPager) getView().findViewById(R.id.viewpager_news);

        mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs));
        mTabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            //subpage(news1,2,3)無法直接進行actuvity，修改可參照Blogfragment
            View view = null;
            switch (position){
                case 0:
                    //Log.e("jjjj", "0");
                    view = new1;
                    container.addView(new1);
                   // view = getLayoutInflater().inflate(R.layout.news1, container, false);
                    break;
                case 1:
                    //Log.e("jjjj", "1");
                    view = new2;
                    container.addView(new2);
                  //  view = getLayoutInflater().inflate(R.layout.news2, container, false);
                    break;
                case 2:
                    //Log.e("jjjj", "2");
                    view = new3;
                    container.addView(new3);
                   // view = getLayoutInflater().inflate(R.layout.news3, container, false);
                    break;


            }
           // container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }





}
