package com.avisys.allinone.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDAO {
@Query("SELECT * FROM person")
List<PersonDetails> getPersonDetails();
@Insert
void addUserDetails(PersonDetails personDetails);
@Update
void updateUser(PersonDetails personDetails);
@Delete
void deleteUserDetails(PersonDetails personDetails);
}
