package com.nativo.sampleapp.NativeAdImpl;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder;
import com.nativo.sampleapp.R;

import net.nativo.sdk.ntvadtype.display.NtvStandardDisplayInterface;

public class StandardDisplayAdRecycler extends RecyclerListViewHolder implements NtvStandardDisplayInterface {

    private CardView layout;
    private WebView webView;

    @Override
    public WebView getContentWebView() {
        return webView;
    }

    @Override
    public void contentWebViewOnPageFinished() {

    }

    @Override
    public void contentWebViewOnReceivedError(String s) {

    }

    @Override
    public int getLayout(Context context) {
        return R.layout.standard_display;
    }

    @Override
    public void bindViews(View view) {
        layout = view.findViewById(R.id.standard_display_layout);
        webView = view.findViewById(R.id.standard_display_webview);
    }

    @Override
    public View getAdContainerView() {
        return null;
    }

    public StandardDisplayAdRecycler(@NonNull View container, ViewGroup viewGroup) {
        super(container, viewGroup);
    }
}
