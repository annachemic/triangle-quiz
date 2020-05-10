package ru.annachemic.natera.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.annachemic.natera.dto.request.CreateTriangleDtoRequest;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;

public interface CreateTriangleService {
@POST("triangle")
Call<TriangleDtoResponse> createTriangle(@Body CreateTriangleDtoRequest createTriangleDtoRequest, @Header("X-User") String userKey);
}
