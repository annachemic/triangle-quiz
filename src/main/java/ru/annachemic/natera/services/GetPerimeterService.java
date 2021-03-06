package ru.annachemic.natera.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import ru.annachemic.natera.dto.response.ResultDto;

public interface GetPerimeterService {
    @GET("/triangle/{triangleId}/perimeter")
    Call<ResultDto> getAPerimeter(@Path("triangleId") String triangleId, @Header("X-User") String userKey);
}
