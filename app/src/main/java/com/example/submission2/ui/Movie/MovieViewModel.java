package com.example.submission2.ui.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission2.Model.MovieDataRes;
import com.example.submission2.Model.MovieModel;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<MovieDataRes>> listMoview = new MutableLiveData<>();

    public MutableLiveData<List<MovieDataRes>> getListMoview() {
        return listMoview;
    }

    public void setListMoview(MutableLiveData<List<MovieDataRes>> listMoview) {
        this.listMoview = listMoview;
    }
}