package com.nativo.nativo_android_unifiedsample.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.NativeAdImpl.NativeAdRecycler;
import com.nativo.nativo_android_unifiedsample.NativeAdImpl.NativeVideoAdRecycler;
import com.nativo.nativo_android_unifiedsample.NativeAdImpl.StandardDisplayAdRecycler;
import com.nativo.nativo_android_unifiedsample.R;
import com.nativo.nativo_android_unifiedsample.SponsoredContentActivity;
import com.nativo.nativo_android_unifiedsample.ViewHolders.RecyclerListViewHolder;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvconstant.NtvAdTypeConstants;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.nativo.nativo_android_unifiedsample.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SECTION_URL;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_CONTAINER_HASH;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_SECTION_URL;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerListViewHolder> implements NtvSectionAdapter {

    private static String TAG = RecyclerViewAdapter.class.getName();
    private Context context;
    private RecyclerView recyclerView;
    List<Integer> integerList = new ArrayList<>();


    public RecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        for (int i = 0; i < 20; i++) {
            integerList.add(i);
        }
    }

    @Override
    public RecyclerListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerListViewHolder viewHolder;
        View adViewTry;
        if (i == 1) {
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_article, viewGroup, false);
            viewHolder = new NativeAdRecycler(adViewTry, viewGroup);
        } else if (i == 2) {
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
            viewHolder = new NativeVideoAdRecycler(adViewTry, viewGroup);
        } else if (i == 3) {
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.standard_display, viewGroup, false);
            viewHolder = new StandardDisplayAdRecycler(adViewTry, viewGroup);
        } else {
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publisher_article, viewGroup, false);
            viewHolder = new RecyclerListViewHolder(adViewTry, viewGroup);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListViewHolder listViewHolder, int i) {
        boolean ad = false;
        View view = listViewHolder.getContainer();
        if (listViewHolder instanceof NativeAdRecycler ||
                listViewHolder instanceof NativeVideoAdRecycler || listViewHolder instanceof StandardDisplayAdRecycler) {
            ad = NativoSDK.getInstance().placeAdInView(view,
                    recyclerView, SECTION_URL, i, this, null);
        }

        if (!ad) {
            bindView(listViewHolder.getContainer(), i);
            NativoSDK.getInstance().placeAdInView(view, recyclerView,
                    SECTION_URL, i, this, null);
        }
    }


    @Override
    public int getItemViewType(int position) {
        String s = NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, recyclerView, position);
        switch (s) {
            case NtvAdTypeConstants.AD_TYPE_STANDARD_DISPLAY:
                return 3;
            case NtvAdTypeConstants.AD_TYPE_VIDEO:
                return 2;
            case NtvAdTypeConstants.AD_TYPE_NATIVE:
                return 1;
            case NtvAdTypeConstants.AD_TYPE_NONE:
                return 0;
            default:
                return -1;
        }
    }

    private void bindView(View view, int i) {
        if (view != null) {
            if (((ImageView) view.findViewById(R.id.article_image)) != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.newsimage);
            }
            if (((ImageView) view.findViewById(R.id.sponsored_ad_indicator)) != null) {
                ((ImageView) view.findViewById(R.id.sponsored_ad_indicator)).setVisibility(View.INVISIBLE);
            }
            if (((TextView) view.findViewById(R.id.article_author)) != null) {
                ((TextView) view.findViewById(R.id.article_author)).setText(R.string.sample_author);
            }
            if (((TextView) view.findViewById(R.id.article_title)) != null) {
                ((TextView) view.findViewById(R.id.article_title)).setText(R.string.sample_title);
            }
            if (((TextView) view.findViewById(R.id.sponsored_tag)) != null) {
                ((TextView) view.findViewById(R.id.sponsored_tag)).setVisibility(View.INVISIBLE);
            }
            view.setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CLICK_OUT_URL)));
        }
    };

    @Override
    public int getItemCount() {
        return integerList.size();
    }


    @Override
    public boolean shouldPlaceAdAtIndex(String s, int i) {
        return i % 2 == 1;
    }

    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    @Override
    public void needsDisplayLandingPage(String s, int i) {
        context.startActivity(new Intent(context, SponsoredContentActivity.class)
                .putExtra(SP_SECTION_URL, s)
                .putExtra(SP_CAMPAIGN_ID, i)
                .putExtra(SP_CONTAINER_HASH, recyclerView.hashCode()));
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String s, NtvAdData ntvAdData) {
        Log.d(TAG, "onReceiveAd: track called");
        notifyDataSetChanged();
    }

    @Override
    public void onFail(String s) {
        // protect against removing non ad views
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
