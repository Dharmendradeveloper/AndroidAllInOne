package com.avisys.allinone.pagination.api;

import com.avisys.allinone.pagination.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
  @GET("users")/*https://reqres.in/api/users?page=6*/
  Call<UserResponse> getUsers(@Query("page") long page);
}