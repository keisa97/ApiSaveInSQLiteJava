package com.keisardevs.mymovieswithsql.model.service;

import com.keisardevs.mymovieswithsql.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {


    @GET("movies.json")
    Call<List<Movie>> getMovies();
}
