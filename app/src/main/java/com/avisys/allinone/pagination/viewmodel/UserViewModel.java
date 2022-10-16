package com.avisys.allinone.pagination.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.avisys.allinone.pagination.data.UserDataSource;
import com.avisys.allinone.pagination.factory.UserDataSourceFactory;
import com.avisys.allinone.pagination.model.User;

public class UserViewModel extends ViewModel {

  public LiveData<PagedList<User>> userPagedList;

//  private LiveData<UserDataSource> liveDataSource;
  private final  String TAG = "UserDataSource";

  public UserViewModel() {
    init();
  }

  private void init() {
    Log.e(TAG,"2::");
    UserDataSourceFactory itemDataSourceFactory = new UserDataSourceFactory();
//    liveDataSource = itemDataSourceFactory.userLiveDataSource;
    PagedList.Config config = new PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(UserDataSource.PAGE_SIZE)
        .build();
    userPagedList = new LivePagedListBuilder<>(itemDataSourceFactory, config).build();
  }
}