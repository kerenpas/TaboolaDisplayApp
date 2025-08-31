package com.example.tabooladisplayapp.data.remote;

import com.example.tabooladisplayapp.data.remote.dto.FeedDto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedApi {
    @GET("home_assignment/data.json")
    Call<List<FeedDto>> getFeed();
}
