package com.example.praty.moviedatabase.rest;

import com.example.praty.moviedatabase.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

   @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

   @GET("movie/{movie_id}")
    Call<MoviesResponse> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
