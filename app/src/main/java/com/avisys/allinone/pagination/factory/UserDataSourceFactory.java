package com.avisys.allinone.pagination.factory;

import android.util.Log;

import androidx.paging.DataSource;

import com.avisys.allinone.pagination.data.UserDataSource;
import com.avisys.allinone.pagination.model.User;

public class UserDataSourceFactory extends DataSource.Factory<Long, User> {
  private final String TAG = "UserDataSource";
//  public MutableLiveData<UserDataSource> userLiveDataSource=new MutableLiveData<>();

  @Override public DataSource<Long, User> create() {
    Log.e(TAG,"3::");
    UserDataSource userDataSource = new UserDataSource();
//    userLiveDataSource.postValue(userDataSource);
    return userDataSource;
  }
}