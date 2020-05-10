package ru.annachemic.natera.services;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeleteTriangleService {
    @DELETE("triangle/{triangleId}")
    Call<Void> deleteTriangleById(@Path("triangleId") String triangleId, @Header("X-User") String userKey);
}
