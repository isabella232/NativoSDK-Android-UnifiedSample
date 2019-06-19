package com.nativo.nativo_android_unifiedsample.ViewFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.nativo.nativo_android_unifiedsample.R;
import com.nativo.nativo_android_unifiedsample.ViewAdapter.GridViewAdapter;

import net.nativo.sdk.NativoSDK;

import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SECTION_URL;

public class GridFragment extends Fragment {

    GridView gridView;
    GridViewAdapter gridViewAdapter;
    public GridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grid, container, false);
        gridView = root.findViewById(R.id.grid_view);
        gridViewAdapter = new GridViewAdapter(getContext(), gridView);
        gridView.setAdapter(new GridViewAdapter(getContext(), gridView));
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NativoSDK.getInstance().clearAdsInSection(SECTION_URL, gridView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gridViewAdapter.cleanAdapter();
    }
}
