package ru.annachemic.natera.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.dto.response.TriangleListInfoDtoResponse;

import java.util.List;

public interface GetTriangleInfoService {
    @GET("triangle/all")
    Call<List<TriangleDtoResponse>> getTriangleList(@Header("X-User") String userKey);
}
