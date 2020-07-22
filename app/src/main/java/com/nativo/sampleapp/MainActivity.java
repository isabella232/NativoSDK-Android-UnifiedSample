package com.nativo.sampleapp;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.nativo.sampleapp.NativeAdImpl.NativeAd;
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAd;
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAd;
import com.nativo.sampleapp.NativeAdLandingImpl.NativeLandingPage;
import com.nativo.sampleapp.NativeAdVideo.FullScreenVideoImpl;
import com.nativo.sampleapp.ViewFragment.DfpFragment;
import com.nativo.sampleapp.ViewFragment.GridFragment;
import com.nativo.sampleapp.ViewFragment.MOAPFragment;
import com.nativo.sampleapp.ViewFragment.RecyclerViewFragment;
import com.nativo.sampleapp.ViewFragment.SingleViewFragment;
import com.nativo.sampleapp.ViewFragment.SingleViewVideoFragment;
import com.nativo.sampleapp.ViewFragment.TableFragment;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvcore.NtvAdData;

import static com.nativo.sampleapp.util.AppConstants.SAMPLE_CCPA_INVALID_CONSENT;
import static com.nativo.sampleapp.util.AppConstants.SAMPLE_CCPA_VALID_CONSENT;
import static com.nativo.sampleapp.util.AppConstants.SAMPLE_GDPR_CONSENT;
import static com.nativo.sampleapp.util.AppConstants.SAMPLE_GDPR_INVALID_CONSENT;
import static net.nativo.sdk.ntvconstant.NtvConstants.CCPA_SHARED_PREFERENCE_STRING;
import static net.nativo.sdk.ntvconstant.NtvConstants.GDPR_SHARED_PREFERENCE_STRING;

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

    private void setPrivacyAndTransparencyKeys() {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(GDPR_SHARED_PREFERENCE_STRING, SAMPLE_GDPR_CONSENT);
        editor.putString(CCPA_SHARED_PREFERENCE_STRING, SAMPLE_CCPA_VALID_CONSENT);
        editor.apply();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.yes:
                setPrivacyAndTransparencyKeys();
                return true;
            case R.id.no:
                removePrivacyAndTransparencyKeys();
                return true;
            case R.id.invalid:
                invalidPrivacyAndTransparencyKeys();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void invalidPrivacyAndTransparencyKeys() {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(GDPR_SHARED_PREFERENCE_STRING, SAMPLE_GDPR_INVALID_CONSENT);
        editor.putString(CCPA_SHARED_PREFERENCE_STRING, SAMPLE_CCPA_INVALID_CONSENT);
        editor.apply();

    }

    private SharedPreferences.Editor getEditor() {
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        return preferenceManager.edit();
    }

    private void removePrivacyAndTransparencyKeys() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(GDPR_SHARED_PREFERENCE_STRING);
        editor.remove(CCPA_SHARED_PREFERENCE_STRING);
        editor.apply();
    }
}
