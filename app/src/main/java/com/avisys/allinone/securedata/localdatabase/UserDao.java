package com.avisys.allinone.securedata.localdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT Count(*) FROM UserData WHERE username =:username AND password =:password")
    int checkUserExists(String username,String password);

    @Query("SELECT Count(*) FROM UserData WHERE username =:username")
    int checkUserNameExists(String username);

    @Insert
    void insertAll(UserData... userData);

}
