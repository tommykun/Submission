package com.example.submission2.ui.Tv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission2.DetailTvActivity;
import com.example.submission2.Model.TvDataRes;
import com.example.submission2.Model.TvModel;
import com.example.submission2.Model.TvResponse;
import com.example.submission2.Network.RetrofitHelper;
import com.example.submission2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvFragment extends Fragment implements SearchView.OnQueryTextListener {

    private LinearLayoutManager linearLayoutManager;
    private TvAdapter adapter;
    private RecyclerView recyclerView;
    private TvViewModel tvViewModel;
    private List<TvDataRes> listModel = new ArrayList<>();
    private int VState;
    private final String VALUE= "VALUE";
    private final String MVALUE="MVALUE";

    Unbinder unbinder;
    ProgressDialog dialog;
    SearchView searchView;

    private Observer<List<TvDataRes>> getTv = new Observer<List<TvDataRes>>() {
        @Override
        public void onChanged(List<TvDataRes> weatherItems) {
            if (weatherItems != null) {
                adapter.setData(weatherItems);
            }
        }

    };

    public TvFragment() {
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tv, container, false);
        unbinder = ButterKnife.bind(this,root);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);

        searchView = root.findViewById(R.id.search_data_tv);
        searchView.setOnQueryTextListener(this);
        recyclerView = root.findViewById(R.id.rv_tv);

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
        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.getListMoview().observe(this,getTv);

        return root;

    }

    private void init() {

        linearLayoutManager= new LinearLayoutManager(getContext());
        adapter = new TvAdapter(getContext(),listModel);
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

        RetrofitHelper.getService().getTv(RetrofitHelper.API_KEY)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        if (response.body() != null) {
                            for (TvDataRes data : response.body().getResults()){
                                listModel.add(data);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Log.d("LoadData","Success");
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<TvResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                        Log.d("DataError",t.getMessage());
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        cariData();
        init();
        return false;
    }

    private void cariData() {

        String cari = searchView.getQuery().toString();
        dialog.show();

        RetrofitHelper.getService().getSearchTv(cari)
                .enqueue(new Callback<TvResponse>() {
                    @Override
                    public void onResponse(Call<TvResponse> call, Response<TvResponse> response) {
                        if (response.body() != null){
                            listModel.clear();
                            for (TvDataRes a : response.body().getResults()){
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
                    public void onFailure(Call<TvResponse> call, Throwable t) {
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