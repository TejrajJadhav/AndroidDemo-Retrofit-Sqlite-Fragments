package com.example.iauro.networks;

import com.example.iauro.model.PhotoData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequests {

    @GET("photos")
    Call<List<PhotoData>> getPhotos();
}
