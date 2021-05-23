package com.example.testimegeapp.networking

import com.example.testimegeapp.ui.models.PhotosSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=1f32cf6016023712b0807ad9f9ce7875&tags=tags")
    suspend fun fetchImages(@Query("tags") tags: String): Response<PhotosSearchResponse>

}