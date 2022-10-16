package com.avisys.allinone.mypagination.apiservice;

import com.avisys.allinone.mypagination.util.ServiceUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private RetrofitServices services;
    private static Retrofit retrofit;


    public static Retrofit getInstance() {
        /*
         *1.As soon as the getInstance is being called then object of this class gets created if already not
         * is not created
         *2.then retrofit creates the object under the @constructor RetrofitBuilder and create
         * the retrofit services as well.
         * 3. once the services is initialised just after that we can invoke the @method buildRetrofitService
         */
        if (retrofit==null) {
            synchronized (RetrofitBuilder.class) {
                 retrofit = new Retrofit.Builder().baseUrl(ServiceUrl.BASE_URL).
                        addConverterFactory(GsonConverterFactory.create()).build();
            }
        }
        return retrofit;
    }

    public static <T> T buildRetrofitService(Class<T> type){
        return retrofit.create(type);
    }


}
