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

import com.example.submission2.Model.MovieDataRes;
import com.example.submission2.Network.Database.FavHelperDb;
import com.example.submission2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FarFragMovie extends Fragment {

    Unbinder unbinder;
    RecyclerView rcv_frag_movie;
    private List<MovieDataRes> tvDataResList;
    private FavHelperDb favHelperDb;
    private FavFragMovieAdapter adapter;


    public FarFragMovie() {
    }

    public static Fragment newInstance(String fragment){
        FarFragMovie farFragMovie = new FarFragMovie();
        Bundle bundle = new Bundle();
        farFragMovie.setArguments(bundle);

        return farFragMovie;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        rcv_frag_movie  = view.findViewById(R.id.rcv_far_movie);
        return view;

    }


    private class LoadDB extends AsyncTask<Void, Void,List<MovieDataRes>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (tvDataResList.size() > 0) {
                tvDataResList.clear();
            }

        }

        @Override
        protected void onPostExecute(List<MovieDataRes> favorites) {
            super.onPostExecute(favorites);
            tvDataResList.addAll(favorites);
            adapter.setListMovie(tvDataResList);
            adapter.notifyDataSetChanged();

            if (tvDataResList.size() == 0) {
                Toast.makeText(getActivity(), "tidak ada data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected List<MovieDataRes> doInBackground(Void... voids) {
            return favHelperDb.getAllMovie();
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
        rcv_frag_movie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv_frag_movie.setHasFixedSize(true);
        favHelperDb = new FavHelperDb(getActivity());
        favHelperDb.open();
        tvDataResList = new ArrayList<>();
        adapter = new FavFragMovieAdapter(getActivity());
        adapter.setListMovie(tvDataResList);
        adapter.notifyDataSetChanged();
        rcv_frag_movie.setAdapter(adapter);
        new LoadDB().execute();
        super.onResume();
    }
}
