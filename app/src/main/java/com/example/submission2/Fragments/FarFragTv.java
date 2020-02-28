package com.example.submission2.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission2.Model.TvDataRes;
import com.example.submission2.Network.Database.FavHelperDb;
import com.example.submission2.R;
import com.example.submission2.ui.Tv.TvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FarFragTv extends Fragment {

    Unbinder unbinder;
    RecyclerView rcv_frag_tv;
    private List<TvDataRes> tvDataResList;
    private FavHelperDb favHelperDb;
    private FavFragTvAdapter adapter;


    public FarFragTv() {
    }

    public static Fragment newInstance(String fragment){
        FarFragTv farFragTv = new FarFragTv();
        Bundle bundle = new Bundle();
        farFragTv.setArguments(bundle);

        return farFragTv;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav2_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        rcv_frag_tv  = view.findViewById(R.id.rcv_far_tv);
        return view;

    }


    private class LoadDB extends AsyncTask<Void, Void,List<TvDataRes>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (tvDataResList.size() > 0) {
                tvDataResList.clear();
            }

        }

        @Override
        protected void onPostExecute(List<TvDataRes> favorites) {
            super.onPostExecute(favorites);
            tvDataResList.addAll(favorites);
            adapter.setListMovie(tvDataResList);
            adapter.notifyDataSetChanged();

            if (tvDataResList.size() == 0) {
                Toast.makeText(getActivity(), "tidak ada data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected List<TvDataRes> doInBackground(Void... voids) {
            return favHelperDb.getAllTv();
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        favHelperDb = new FavHelperDb(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favHelperDb != null) {
            favHelperDb.close();
        }
    }
    @Override
    public void onResume() {
        rcv_frag_tv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv_frag_tv.setHasFixedSize(true);
        favHelperDb = new FavHelperDb(getActivity());
        favHelperDb.open();
        tvDataResList = new ArrayList<>();
        adapter = new FavFragTvAdapter(getActivity());
        adapter.setListMovie(tvDataResList);
        rcv_frag_tv.setAdapter(adapter);
        new LoadDB().execute();
        super.onResume();
    }

}
