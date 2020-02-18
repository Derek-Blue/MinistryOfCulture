package com.example.ministryofculture;

import com.example.ministryofculture.Model.Api_post;
import com.example.ministryofculture.Model.GetApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIservice {

    @GET("emapOpenDataJsonAction.do?method=exportEmapJsonByMainType&mainType=1")
    Call<List<GetApi>> getPost();

    @GET("emapOpenDataJsonAction.do?method=exportEmapJsonByMainType&mainType=1")
    Call<List<GetApi>> searchName(@Query("name")  String name);
}
