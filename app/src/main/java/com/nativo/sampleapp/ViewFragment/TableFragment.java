package com.nativo.sampleapp.ViewFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.nativo.sampleapp.ViewAdapter.TableViewAdapter;
import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;

public class TableFragment extends Fragment {

    private ListView listView;
    private TableViewAdapter tableViewAdapter;
    private static String SECTION_URL = "http://www.nativo.net/test/";

    public TableFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        listView = view.findViewById(R.id.list_table);
        listView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        tableViewAdapter = new TableViewAdapter(listView);
        listView.setAdapter(tableViewAdapter);
        return view;
    }

    View.OnClickListener loadAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NativoSDK.getInstance().clearAdsInSection(SECTION_URL, listView);
            tableViewAdapter.notifyDataSetChanged();
        }
    };

    View.OnClickListener hideAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listView.setVisibility(View.INVISIBLE);
        }
    };

    View.OnClickListener showAd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listView.setVisibility(View.VISIBLE);
        }
    };

    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Log.d(getTag(), "position in view " + listView.getFirstVisiblePosition());
        }
    };

}