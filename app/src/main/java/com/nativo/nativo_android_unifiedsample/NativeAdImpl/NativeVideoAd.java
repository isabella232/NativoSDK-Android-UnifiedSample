package com.nativo.nativo_android_unifiedsample.NativeAdImpl;

import android.content.Context;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.R;

import net.nativo.sdk.ntvadtype.video.NtvVideoAdInterface;
import net.nativo.sdk.ntvadtype.video.VideoPlaybackError;

import java.util.Date;

public class NativeVideoAd implements NtvVideoAdInterface {

    private static String TAG = NativeVideoAd.class.getName();
    private RelativeLayout layout;
    private TextureView textureView;
    private ImageView previewImage;
    private ImageView playButton;
    private ImageView restartButton;
    private TextView titleLabel;
    private TextView authorLabel;
    private ImageView sponsoredIndicator;
    private ProgressBar progressBar;
    private View adContainerView;


    @Override
    public int getLayout(Context context) {
        return R.layout.video_layout;
    }

    @Override
    public View getAdContainerView() {
        return adContainerView;
    }

    @Override
    public void bindViews(View v) {
        adContainerView = v;
        layout = (RelativeLayout) v.findViewById(R.id.video_container);
        textureView = (TextureView) v.findViewById(R.id.video);
        previewImage = (ImageView) v.findViewById(R.id.preview_image);
        playButton = (ImageView) v.findViewById(R.id.play);
        restartButton = (ImageView) v.findViewById(R.id.restart);
        titleLabel = (TextView) v.findViewById(R.id.title);
        authorLabel = (TextView) v.findViewById(R.id.author);
        progressBar = v.findViewById(R.id.video_progress_bar);
    }

    @Override
    public View getRootView() {
        return layout;
    }

    @Override
    public TextureView getTextureView() {
        return textureView;
    }

    @Override
    public ImageView getPreviewImage() {
        return previewImage;
    }

    @Override
    public ImageView getPlayButton() {
        return playButton;
    }

    @Override
    public ImageView getRestartButton() {
        return restartButton;
    }

    @Override
    public TextView getTitleLabel() {
        return titleLabel;
    }

    @Override
    public TextView getAuthorLabel() {
        return authorLabel;
    }

    @Override
    public TextView getPreviewTextLabel() {
        return null;
    }

    @Override
    public ImageView getAuthorImageView() {
        return null;
    }

    @Override
    public TextView getDateLabel() {
        return null;
    }

    @Override
    public void displaySponsoredIndicators(boolean isSponsored) {
        if (isSponsored && layout != null) {
            layout.setBackgroundResource(R.drawable.sponsored_border);
        } else {
            if (layout != null) {
                layout.setBackground(null);
            }
        }
    }

    @Override
    public String formatDate(Date date) {
        return null;
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void onVideoEnteredFullscreen() {
        Log.d(TAG, "onVideoEnteredFullscreen: ");
    }

    @Override
    public void onVideoExitedFullscreen() {
        Log.d(TAG, "onVideoExitedFullscreen: ");
    }

    @Override
    public void onVideoPlay() {
        Log.d(TAG, "onVideoPlay: ");
    }

    @Override
    public void onVideoPause() {
        Log.d(TAG, "onVideoPause: ");
    }

    @Override
    public void onVideoPlaybackCompleted() {
        Log.d(TAG, "onVideoPlaybackCompleted: ");
    }

    @Override
    public void onVideoPlaybackError(VideoPlaybackError videoPlaybackError) {
        Log.d(TAG, "onVideoPlaybackError: ");
    }
}
