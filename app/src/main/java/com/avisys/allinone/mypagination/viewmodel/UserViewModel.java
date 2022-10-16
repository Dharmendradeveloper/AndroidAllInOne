package com.avisys.allinone.mypagination.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.avisys.allinone.mypagination.data.factory.UserDataSourceFactory;
import com.avisys.allinone.mypagination.data.model.User;
import com.avisys.allinone.mypagination.util.CommonUtils;

public class UserViewModel extends ViewModel {
    private LiveData<String> failedMsg;

    public LiveData<PagedList<User>> init() {
        UserDataSourceFactory userDataSourceFactory = new UserDataSourceFactory();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(CommonUtils.PAGE_SIZE)
                .build();
        failedMsg = userDataSourceFactory.getFailedMsg();
       return new LivePagedListBuilder<>(userDataSourceFactory,config).build();
    }

    public LiveData<String> getErrorMessage(){
        return failedMsg;
    }

}
