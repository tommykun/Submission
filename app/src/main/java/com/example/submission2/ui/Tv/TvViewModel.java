package com.example.submission2.ui.Tv;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission2.Model.TvDataRes;

import java.util.List;

public class TvViewModel extends ViewModel {

    private MutableLiveData<List<TvDataRes>> listMoview = new MutableLiveData<>();

    public MutableLiveData<List<TvDataRes>> getListMoview() {
        return listMoview;
    }

    public void setListMoview(MutableLiveData<List<TvDataRes>> listMoview) {
        this.listMoview = listMoview;
    }
}