package ru.annachemic.natera.rest;

import lombok.SneakyThrows;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.services.GetTriangleInfoService;
import ru.annachemic.natera.utils.RetrofitUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


public class RestMethods {
    @SneakyThrows
    public static List<TriangleDtoResponse> getAllTrianglesInfo() {
        GetTriangleInfoService getTriangleInfoService = RetrofitUtils.getRetrofit()
                .create(GetTriangleInfoService.class);
        return Objects.requireNonNull(getTriangleInfoService.getTriangleList("f78ed3e2-2e7d-4e54-8c41-ffbc715bea1c").execute().body());
    }

    public static List<String> getAllTringlesIds(List<TriangleDtoResponse> list){
        return list.stream().map(c -> c.getId().toString()).collect(Collectors.toList());
    }

}
