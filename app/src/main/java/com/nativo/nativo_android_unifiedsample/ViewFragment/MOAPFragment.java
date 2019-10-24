package com.nativo.nativo_android_unifiedsample.ViewFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.nativo.nativo_android_unifiedsample.R;

import net.nativo.sdk.NativoSDK;

import static com.nativo.nativo_android_unifiedsample.util.AppConstants.MOAP_SECTION_URL;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.PUBLISHER_URL;

public class MOAPFragment extends Fragment {

    WebView mWebView;

    public MOAPFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWebView(view);
        EditText editText = view.findViewById(R.id.moap_editText);
        Button button = view.findViewById(R.id.moap_button);
        button.setOnClickListener(v -> {
            String url = editText.getText().toString();
            boolean validUrl = URLUtil.isValidUrl(url);
            if (validUrl) {
                mWebView.loadUrl(url);
            } else {
                Toast.makeText(getContext(),"Invalid Url LOAD URL not called, ensure HTTP/S prefix", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupWebView(@NonNull View view) {
        mWebView = view.findViewById(R.id.moap_webview);
        WebView.setWebContentsDebuggingEnabled(true);
        mWebView.clearCache(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebChromeClient(new NativoChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                NativoSDK.getInstance().placeAdInWebView(mWebView, MOAP_SECTION_URL);
            }
        });

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        getActivity().onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });

        mWebView.loadUrl(PUBLISHER_URL);
    }

    private class NativoChromeClient extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        NativoChromeClient() {
        }

        public void onHideCustomView() {
            ((FrameLayout) getActivity().getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getActivity().getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            getActivity().setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getActivity().getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getActivity().getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getActivity().getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
