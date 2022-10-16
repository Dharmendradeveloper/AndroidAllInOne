package com.avisys.allinone.mypagination.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.avisys.allinone.mypagination.apiservice.RetrofitBuilder;
import com.avisys.allinone.mypagination.apiservice.RetrofitServices;
import com.avisys.allinone.mypagination.data.model.User;
import com.avisys.allinone.mypagination.data.model.UserResponse;
import com.avisys.allinone.mypagination.util.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataSourceRepo extends PageKeyedDataSource<Long, User>  {
    private MutableLiveData<String> failedMsg;

    public UserDataSourceRepo() {
       failedMsg = new MutableLiveData<>();
    }

    public LiveData<String> getErrorMessage(){
        return failedMsg;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull LoadInitialCallback<Long, User> callback) {
        Call<UserResponse> userResponseCall = RetrofitBuilder.getInstance().create(RetrofitServices.class)
                .getUsersDetails(CommonUtils.FIRST_PAGE);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    failedMsg.postValue(response.message());
                    return;
                }
                if (response.body()==null){
                    failedMsg.postValue("Data not found");
                    return;
                }
                callback.onResult(response.body().getUsers(),null,CommonUtils.FIRST_PAGE+1);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                failedMsg.postValue(t.getMessage());
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, User> callback) {

//        Call<UserResponse> call = RetrofitBuilder.getInstance().create(RetrofitServices.class)
//                .getUsersDetails(params.key);
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (!response.isSuccessful()){
//                    failedMsg.postValue(response.message());
//                    return;
//                }
//                if (response.body()==null){
//                    failedMsg.postValue("Data not found");
//                    return;
//                }
//                    long mkey;
//                    if (params.key>1)
//                        mkey = params.key-1;
//                    else
//                        mkey = 0;
//                    callback.onResult(response.body().getUsers(),mkey);

//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                failedMsg.postValue(t.getMessage());
//            }
//        });
    }



    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, User> callback) {
        Call<UserResponse> call = RetrofitBuilder.getInstance().create(RetrofitServices.class)
                .getUsersDetails(params.key);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()){
                    failedMsg.postValue(response.message());
                    return;
                }
                if (response.body()==null){
                    failedMsg.postValue("Data not found");
                    return;
                }

                callback.onResult(response.body().getUsers(),params.key+1);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                failedMsg.postValue(t.getMessage());
            }
        });
    }



//    private void getRepository(long page){
//        if (userRepository==null)
//            userRepository = UserRepository.getRepository();
//        Log.e("Repo Object::"," "+userRepository);
//        userRepository.fetchUserDetails(page);
//
//    }


}
