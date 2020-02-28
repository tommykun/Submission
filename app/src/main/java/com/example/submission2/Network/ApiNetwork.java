package com.example.submission2.Network;

import com.example.submission2.Model.MovieResponse;
import com.example.submission2.Model.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiNetwork {

    @Headers("Accept: application/json")
    @GET("3/discover/movie?")
    Call<MovieResponse> getMovie(@Query("api_key") String api_key);

    @Headers("Accept: application/json")
    @GET("3/discover/tv?")
    Call<TvResponse> getTv(@Query("api_key") String api_key);

    @Headers("Accept: application/json")
    @GET("3/search/movie?api_key="+RetrofitHelper.API_KEY)
    Call<MovieResponse> getSearch(@Query("query") String movie_name);

    @Headers("Accept: application/json")
    @GET("3/search/tv?api_key="+RetrofitHelper.API_KEY)
    Call<TvResponse> getSearchTv(@Query("query") String tv_name);


    @Headers("Accept: application/json")
    @GET("3/discover/movie")
    Call<MovieResponse> getReleaseTodayMovie(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String gteDate,
            @Query("primary_release_date.lte") String lteDate
    );

    @Headers("Accept: application/json")
    @GET("3/discover/movie")
    Call<TvResponse> getReleaseTodayTv(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String gteDate,
            @Query("primary_release_date.lte") String lteDate
    );

}
