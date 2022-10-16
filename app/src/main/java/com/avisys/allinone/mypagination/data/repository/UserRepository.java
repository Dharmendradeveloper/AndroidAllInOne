package com.avisys.allinone.mypagination.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.avisys.allinone.mypagination.apiservice.RetrofitBuilder;
import com.avisys.allinone.mypagination.apiservice.RetrofitServices;
import com.avisys.allinone.mypagination.data.model.User;
import com.avisys.allinone.mypagination.data.model.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static UserRepository repository=null;
    private MutableLiveData<String> failedMsg;
    private List<User> userData;


    private UserRepository(){
      failedMsg = new MutableLiveData<>();
      userData = new ArrayList<>();
    }

    public synchronized static UserRepository getRepository() {
            if (repository==null)
                repository = new UserRepository();
        return repository;
    }

    public List<User> getUserDetails(){
        return userData;
    }

    public LiveData<String> getErrorMessage(){
        return failedMsg;
    }

    public void fetchUserDetails(long page){
        Call<UserResponse> repositoryCall = RetrofitBuilder.getInstance().create(RetrofitServices.class)
                .getUsersDetails(page);

        repositoryCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    failedMsg.postValue(response.message());
                    return;
                }
                if (response.body()==null) {
                    failedMsg.postValue("Data not found");
                    return;
                }
                userData.addAll(response.body().getUsers());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                failedMsg.postValue(t.getMessage());
            }
        });

    }
}
