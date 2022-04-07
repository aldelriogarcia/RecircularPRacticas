package com.example.prueba;

import retrofit2.Call;
import retrofit2.http.POST;

public interface APIToken {

    @POST("androidAppToken")
    Call<APIRespuesta> createPost(String token);
}
