package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Rating;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lucky on 30-Oct-17.
 */

public interface RatingService {
    @POST("/api/ratings")
    Call<Rating> postRating(@Body Rating rating);
}
