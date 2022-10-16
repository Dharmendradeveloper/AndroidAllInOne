package com.avisys.allinone.mypagination.data.factory;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.avisys.allinone.mypagination.data.model.User;
import com.avisys.allinone.mypagination.data.repository.UserDataSourceRepo;

public class UserDataSourceFactory extends DataSource.Factory<Long, User> {
    public LiveData<String> failedMsg;

    public UserDataSourceFactory() {

    }
    /**
     * Here Don't wonder by seeing the dataSource returns as UserDataSource but A/C to return type in
     * @method DataSource<Long,User > which is in reality is the parent of USerDataSource from Hierarchy.
     *
     * */

    @Override
    public DataSource<Long, User> create() {
        UserDataSourceRepo dataSourceRepo = new UserDataSourceRepo();
        failedMsg = dataSourceRepo.getErrorMessage();
        return dataSourceRepo;
    }

    public LiveData<String> getFailedMsg(){
        return failedMsg;
    }
}
