package com.keisardevs.mymovieswithsql.model.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.keisardevs.mymovieswithsql.Common.MOVIEDB_API_URL;

public class RetrofitClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(MOVIEDB_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            System.out.println(retrofit);
        }
        return retrofit;
    }
}
