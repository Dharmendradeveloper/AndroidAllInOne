package com.avisys.allinone.mypagination.apiservice;

import com.avisys.allinone.mypagination.data.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitServices {
    @GET("users")/*https://reqres.in/api/users?page=1*/
    Call<UserResponse> getUsersDetails(@Query("page") long page);
}
