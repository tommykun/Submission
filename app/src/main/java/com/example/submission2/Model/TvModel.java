package com.example.submission2.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TvModel implements Parcelable {

    private String judul;
    private int poster;
    private String Deskripsi;

    public TvModel() {
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeInt(poster);
        dest.writeString(Deskripsi);
    }
    protected TvModel(Parcel in) {
        judul = in.readString();
        poster = in.readInt();
        Deskripsi = in.readString();
    }

    public static final Creator<TvModel> CREATOR = new Creator<TvModel>() {
        @Override
        public TvModel createFromParcel(Parcel in) {
            return new TvModel(in);
        }

        @Override
        public TvModel[] newArray(int size) {
            return new TvModel[size];
        }
    };
}
