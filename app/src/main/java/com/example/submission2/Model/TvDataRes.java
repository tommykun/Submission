package com.example.submission2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvDataRes implements Parcelable {

    @SerializedName("original_name")
    private String originalName;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<>();
    @SerializedName("name")
    private String name;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("origin_country")
    private List<String> originCountry = new ArrayList<>();
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("id")
    private Integer id;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    private String ID;
    private String TYPE;

    public TvDataRes() {
    }

    public TvDataRes(String originalName, List<Integer> genreIds, String name, Double popularity, List<String> originCountry, Integer voteCount, String firstAirDate, String backdropPath, String originalLanguage, Integer id, Integer voteAverage, String overview, String posterPath) {
        this.originalName = originalName;
        this.genreIds = genreIds;
        this.name = name;
        this.popularity = popularity;
        this.originCountry = originCountry;
        this.voteCount = voteCount;
        this.firstAirDate = firstAirDate;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.id = id;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public TvDataRes(int id, String id1, String judul, String type, String description, String posterpath) {
        this.ID = id1;
        this.originalName = judul;
        this.TYPE = type;
        this.overview = description;
        this.posterPath = posterpath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public static Creator<TvDataRes> getCREATOR() {
        return CREATOR;
    }

    protected TvDataRes(Parcel in) {
        originalName = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        originCountry = in.createStringArrayList();
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
        firstAirDate = in.readString();
        backdropPath = in.readString();
        originalLanguage = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        voteAverage = in.readDouble();
        overview = in.readString();
        posterPath = in.readString();
        ID = in.readString();
        TYPE = in.readString();
    }

    public static final Creator<TvDataRes> CREATOR = new Creator<TvDataRes>() {
        @Override
        public TvDataRes createFromParcel(Parcel in) {
            return new TvDataRes(in);
        }

        @Override
        public TvDataRes[] newArray(int size) {
            return new TvDataRes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalName);
        dest.writeString(name);
        if (popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(popularity);
        }
        dest.writeStringList(originCountry);
        if (voteCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(voteCount);
        }
        dest.writeString(firstAirDate);
        dest.writeString(backdropPath);
        dest.writeString(originalLanguage);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeDouble(voteAverage);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(ID);
        dest.writeString(TYPE);
    }
}
