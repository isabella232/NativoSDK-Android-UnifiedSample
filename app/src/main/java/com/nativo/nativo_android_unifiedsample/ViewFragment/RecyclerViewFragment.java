package com.nativo.nativo_android_unifiedsample.ViewFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nativo.nativo_android_unifiedsample.R;
import com.nativo.nativo_android_unifiedsample.ViewAdapter.RecyclerViewAdapter;

import net.nativo.sdk.NativoSDK;

import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SECTION_URL;

public class RecyclerViewFragment extends Fragment {

    RecyclerView recyclerView;

    public RecyclerViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_list_view, container, false);
        RecyclerViewAdapter viewAdapter = new RecyclerViewAdapter(getContext(), recyclerView);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    // calling clear ads in section when your app transitions to new activity or fragment
    @Override
    public void onPause() {
        super.onPause();
    }
}
