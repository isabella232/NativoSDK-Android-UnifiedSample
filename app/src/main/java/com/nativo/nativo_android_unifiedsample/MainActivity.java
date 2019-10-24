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

import net.nativo.sdk.NativoSDK;

public class MainActivity extends AppCompatActivity {

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
        NativoSDK.getInstance().init(this);
        NativoSDK.getInstance().registerNativeAd(new NativeAd());
        NativoSDK.getInstance().registerLandingPage(new NativeLandingPage());
        NativoSDK.getInstance().registerVideoAd(new NativeVideoAd());
        // Can use the default implementation provided by the SDK
//        NativoSDK.getInstance().registerFullscreenVideo(new DefaultFullscreenVideo());
        NativoSDK.getInstance().registerFullscreenVideo(new FullScreenVideoImpl());
        NativoSDK.getInstance().registerStandardDisplayAd(new StandardDisplayAd());
//        NativoSDK.getInstance().enableTestAdvertisements(NtvAdData.NtvAdType.IN_FEED_AUTO_PLAY_VIDEO);
        NativoSDK.getInstance().enableDevLogs();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        NativoSDK.getInstance().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NativoSDK.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NativoSDK.getInstance().onResume();
    }

    private class FragmentViewAdapter extends FragmentPagerAdapter {


        public FragmentViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new MOAPFragment();
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
                    return new RecyclerViewFragment();

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
                    return getResources().getText(R.string.moap);
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
                    return getResources().getText(R.string.recycle_tab);
            }
            return null;
        }
    }
}
