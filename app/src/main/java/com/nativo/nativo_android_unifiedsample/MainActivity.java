package com.nativo.nativo_android_unifiedsample;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nativo.nativo_android_unifiedsample.NativeAdImpl.NativeAd;
import com.nativo.nativo_android_unifiedsample.NativeAdImpl.NativeVideoAd;
import com.nativo.nativo_android_unifiedsample.NativeAdImpl.StandardDisplayAd;
import com.nativo.nativo_android_unifiedsample.NativeAdLandingImpl.NativeLandingPage;
import com.nativo.nativo_android_unifiedsample.NativeAdVideo.FullScreenVideoImpl;
import com.nativo.nativo_android_unifiedsample.ViewFragment.DfpFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.GridFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.MOAPFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.RecyclerViewFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.SingleViewFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.SingleViewVideoFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.TableFragment;

import net.nativo.sdk.NativoAPI;
import net.nativo.sdk.ntvcore.NtvAdData;

public class MainActivity extends AppCompatActivity {
    NativoAPI nativoAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentViewAdapter fragmentViewAdapter = new FragmentViewAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(fragmentViewAdapter);
        viewPager.setOffscreenPageLimit(0);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        init();
    }

    private void init() {
        nativoAPI = new NativoAPI();
        nativoAPI.init(this);
        nativoAPI.registerNativeAd(new NativeAd());
        nativoAPI.registerLandingPage(new NativeLandingPage());
        nativoAPI.registerVideoAd(new NativeVideoAd());
        // Can use the default implementation provided by the SDK
//        nativoAPI.registerFullscreenVideo(new DefaultFullscreenVideo());
        nativoAPI.registerFullscreenVideo(new FullScreenVideoImpl());
        nativoAPI.registerStandardDisplayAd(new StandardDisplayAd());
        nativoAPI.enableTestAdvertisements(NtvAdData.NtvAdType.NO_FILL);
        nativoAPI.enableDevLogs();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        nativoAPI.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        nativoAPI.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nativoAPI.onResume();
    }

    private class FragmentViewAdapter extends FragmentPagerAdapter {


        public FragmentViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new RecyclerViewFragment();
                case 1:
                    return new GridFragment();
                case 2:
                    return new TableFragment();
                case 3:
                    return new SingleViewFragment();
                case 4:
                    return new SingleViewVideoFragment();
                case 5:
                    return new DfpFragment();
                case 6:
                    return new MOAPFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.recycle_list_tab);
                case 1:
                    return getResources().getText(R.string.grid_tab);
                case 2:
                    return getResources().getText(R.string.table_tab);
                case 3:
                    return getResources().getText(R.string.single_view);
                case 4:
                    return getResources().getText(R.string.single_view_video);
                case 5:
                    return getResources().getText(R.string.dfp);
                case 6:
                    return getResources().getText(R.string.moap);
            }
            return null;
        }
    }
}
