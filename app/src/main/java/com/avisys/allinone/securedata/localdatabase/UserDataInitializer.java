package com.avisys.allinone.securedata.localdatabase;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

public class UserDataInitializer {

    private static void populateAsync(@NonNull AppDatabase appDatabase,@NonNull UserData userData){
        PopulateDBAsync populateDBAsync = new PopulateDBAsync(appDatabase,userData);
        populateDBAsync.execute();
    }

    private static void populateWithTestData(AppDatabase appDatabase,UserData userData){
        addUser(appDatabase,userData);
    }

    private static void addUser(AppDatabase appDatabase,UserData userData){
        appDatabase.userDao().insertAll(userData);
    }
    private static class PopulateDBAsync extends AsyncTask<Void,Void,Void>{
        private AppDatabase appDatabase;
        private UserData userData;

        PopulateDBAsync(AppDatabase appDatabase, UserData userData){
            this.appDatabase = appDatabase;
            this.userData = userData;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(appDatabase,userData);
            return null;
        }
    }
}
