package com.example.submission2.ui.Movie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission2.DetailMovieActivity;
import com.example.submission2.DetailTvActivity;
import com.example.submission2.Model.MovieDataRes;
import com.example.submission2.Model.MovieModel;
import com.example.submission2.Model.MovieResponse;
import com.example.submission2.Network.ApiNetwork;
import com.example.submission2.Network.RetrofitHelper;
import com.example.submission2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment implements SearchView.OnQueryTextListener {

    private LinearLayoutManager linearLayoutManager;
    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private MovieViewModel movieViewModel;
    private List<MovieDataRes> listModel = new ArrayList<>();
    private int VState;
    private final String VALUE= "VALUE";
    private final String MVALUE="MVALUE";

    Unbinder unbinder;
    ProgressDialog dialog;
    SearchView searchView;


    private Observer<List<MovieDataRes>> getMovie = new Observer<List<MovieDataRes>>() {
        @Override
        public void onChanged(List<MovieDataRes> weatherItems) {
            if (weatherItems != null) {
                adapter.setData(weatherItems);
            }
        }

    };

    public MovieFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        unbinder = ButterKnife.bind(this,root);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);

        searchView = root.findViewById(R.id.search_data_movie);
        searchView.setOnQueryTextListener(this);
        recyclerView = root.findViewById(R.id.rv_movie);

        init();
        if (savedInstanceState != null){
            VState = savedInstanceState.getInt(VALUE);
            listModel = savedInstanceState.getParcelableArrayList(MVALUE);
            if (listModel != null && listModel.size() > 0){
                adapter.setData(listModel);
            }else{
                loadDataFromApi();
                init();
            }
        }else{
            loadDataFromApi();
            init();
        }
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getListMoview().observe(this,getMovie);


        return root;

    }
    private void init() {
        linearLayoutManager= new LinearLayoutManager(getContext());
        adapter = new MovieAdapter(getContext(),listModel);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(VALUE, VState);
        outState.putParcelableArrayList(MVALUE, (ArrayList<? extends Parcelable>) listModel);
        super.onSaveInstanceState(outState);
    }




    private void loadDataFromApi() {

        dialog.show();
        RetrofitHelper.getService().getMovie(RetrofitHelper.API_KEY)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null) {
                            listModel.clear();
                            for (MovieDataRes data : response.body().getResults()){
                                listModel.add(data);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Log.d("LoadData","Success");
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        caridata();
        init();
        return false;
    }

    private void caridata() {

        String cari = searchView.getQuery().toString();
        dialog.show();

        RetrofitHelper.getService().getSearch(cari)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null){
                            listModel.clear();
                            for (MovieDataRes a : response.body().getResults()){
                                listModel.add(a);
                                Log.d("LoadData","onResponse : "+a);
                            }
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }else{
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Gagal Mengambil data.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}