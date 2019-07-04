package com.example.foodyappversion2.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.control.ControlerQuanAn;

public class FragmentPlaces extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String latitue,longtitue;
    private Location locationHienTai;
    NestedScrollView nestedScrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_fragment,container,false);
        recyclerView = view.findViewById(R.id.recycle_view);
        progressBar = view.findViewById(R.id.progress_barPlaces);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("toado", Context.MODE_PRIVATE);
        latitue = sharedPreferences.getString("latitue","");
        longtitue = sharedPreferences.getString("longtitue","");
        locationHienTai = new Location("");
        locationHienTai.setLatitude(Double.parseDouble(latitue));
        locationHienTai.setLongitude(Double.parseDouble(longtitue));
        ControlerQuanAn controlerQuanAn = new ControlerQuanAn();
        controlerQuanAn.getDataQuanAn(recyclerView,locationHienTai,getContext(),progressBar,nestedScrollView);
    }
}
