package com.nativo.sampleapp.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.sampleapp.NativeAdImpl.NativeAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAdRecycler;
import com.nativo.sampleapp.SponsoredContentActivity;
import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder;
import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvconstant.NativoAdType;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.sampleapp.util.AppConstants.SECTION_URL;
import static com.nativo.sampleapp.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.sampleapp.util.AppConstants.SP_CONTAINER_HASH;
import static com.nativo.sampleapp.util.AppConstants.SP_SECTION_URL;
import static net.nativo.sdk.ntvconstant.NativoAdType.*;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerListViewHolder> implements NtvSectionAdapter {

    private static String TAG = RecyclerViewAdapter.class.getName();
    private Context context;
    private RecyclerView recyclerView;
    List<Integer> integerList = new ArrayList<>();
    Set<Integer> adsRequestIndex = new HashSet<>();


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
        if (shouldPlaceAdAtIndex(SECTION_URL, i)) {
            adsRequestIndex.add(i);
        }
    }


    @Override
    public int getItemViewType(int position) {
        NativoAdType s = NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, recyclerView, position);
        switch (s) {
            case AD_TYPE_STANDARD_DISPLAY:
                return 3;
            case AD_TYPE_VIDEO:
                return 2;
            case AD_TYPE_NATIVE:
                return 1;
            case AD_TYPE_NONE:
                return 0;
            default:
                return -1;
        }
    }

    private void bindView(View view, int i) {
        if (view != null) {
            if (view.findViewById(R.id.article_image) != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.newsimage);
            }
            if (view.findViewById(R.id.sponsored_ad_indicator) != null) {
                view.findViewById(R.id.sponsored_ad_indicator).setVisibility(View.INVISIBLE);
            }
            if (view.findViewById(R.id.article_author) != null) {
                ((TextView) view.findViewById(R.id.article_author)).setText(R.string.sample_author);
            }
            if (view.findViewById(R.id.article_title) != null) {
                ((TextView) view.findViewById(R.id.article_title)).setText(R.string.sample_title);
            }
            if (view.findViewById(R.id.sponsored_tag) != null) {
                view.findViewById(R.id.sponsored_tag).setVisibility(View.INVISIBLE);
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
        notifyDataSetChanged();
    }

    @Override
    public void onFail(String s) {
        // protect against removing non ad views
        for (Integer index : adsRequestIndex) {
            NativoAdType adTypeForIndex = NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, recyclerView, index);
            if (AD_TYPE_NOFILL.equals(adTypeForIndex)) {
                integerList.remove(index);
                notifyItemRemoved(index);
                notifyItemChanged(index);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerListViewHolder holder) {
        if (holder instanceof NativeVideoAdRecycler) {
            TextureView textureView = ((NativeVideoAdRecycler) holder).getTextureView();
            ((ViewGroup) holder.itemView).removeView(textureView);

        }
        super.onViewRecycled(holder);
    }
}
