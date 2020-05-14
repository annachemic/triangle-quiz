package ru.annachemic.natera.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;

import java.util.List;

public interface GetTriangleInfoService {
    @GET("triangle/all")
    Call<List<TriangleDtoResponse>> getTriangleList(@Header("X-User") String userKey);

    @GET("triangle/{triangleId}")
    Call<TriangleDtoResponse> getTriangleInfoById(@Path("triangleId") String triangleId, @Header("X-User") String userKey);

}
