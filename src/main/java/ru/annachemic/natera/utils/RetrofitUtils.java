package ru.annachemic.natera.utils;

import lombok.experimental.UtilityClass;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@UtilityClass
public class RetrofitUtils {
    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ConfigUtils.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
