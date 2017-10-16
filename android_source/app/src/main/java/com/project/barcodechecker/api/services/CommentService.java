package com.project.barcodechecker.api.services;


import com.project.barcodechecker.models.Comment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lucky on 15-Oct-17.
 */


public interface CommentService {
    @POST("/api/comments")
    Call<Comment> postComment(@Body Comment comment);
}
