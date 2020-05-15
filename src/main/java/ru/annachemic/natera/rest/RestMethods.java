package ru.annachemic.natera.rest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;
import ru.annachemic.natera.dto.request.CreateTriangleDtoRequest;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.services.CreateTriangleService;
import ru.annachemic.natera.services.DeleteTriangleService;
import ru.annachemic.natera.services.GetTriangleInfoService;
import ru.annachemic.natera.utils.ConfigUtils;
import ru.annachemic.natera.utils.RetrofitUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.annachemic.natera.utils.CommonResponseMatchers.checkStatusCodeStep;

@Slf4j
public class RestMethods {
    private static final String INPUT = "3;4;5";

    @SneakyThrows
    public static List<TriangleDtoResponse> getAllTrianglesInfo(String userKey) {
        GetTriangleInfoService getTriangleInfoService = RetrofitUtils.getRetrofit()
                .create(GetTriangleInfoService.class);
        return Objects.requireNonNull(getTriangleInfoService.getTriangleList(userKey).execute().body());
    }

    public static List<TriangleDtoResponse> getAllTrianglesInfo() {
        return getAllTrianglesInfo(ConfigUtils.getUserToken());
    }


    public static List<String> getAllTringlesIds(List<TriangleDtoResponse> list) {
        return list.stream().map(c -> c.getId().toString()).collect(Collectors.toList());
    }


    public static void deleteATriangle(String id, Integer expectedCode) {
        deleteATriangle(id, ConfigUtils.getUserToken(), expectedCode);
    }

    @SneakyThrows
    public static void deleteATriangle(String id, String userKey, Integer expectedCode) {
        DeleteTriangleService deleteTriangleService = RetrofitUtils.getRetrofit().create(DeleteTriangleService.class);
        log.info("Deleting element with id  " + id);
        retrofit2.Response<Void> response =
                deleteTriangleService.deleteTriangleById(
                        id, userKey)
                        .execute();
        checkStatusCodeStep(response, expectedCode);
        if (expectedCode != 200) log.info(response.errorBody().string());
    }

    @SneakyThrows
    public static Response<TriangleDtoResponse> createATriangle(String separator, String input, String userKey) {
        CreateTriangleService createTriangleService = RetrofitUtils.getRetrofit().create(CreateTriangleService.class);
        retrofit2.Response<TriangleDtoResponse> response =
                createTriangleService.createTriangle(
                        CreateTriangleDtoRequest
                                .builder()
                                .separator(separator)
                                .input(input)
                                .build(), userKey)
                        .execute();
        return response;
    }

    public static Response<TriangleDtoResponse> createATriangle(String separator, String input) {
        return createATriangle(separator, input, ConfigUtils.getUserToken());
    }

    public static TriangleDtoResponse createATriangle() {
        return createATriangle(null, INPUT).body();
    }

    public static TriangleDtoResponse createATriangle(Double firstSide, Double secondSide, Double thirdSide) {
        String input = firstSide.toString() + ";" + secondSide.toString() + ";" + thirdSide.toString();
        return createATriangle(null, input).body();
    }


    public static String getNewTriangleId() {
        return createATriangle(null, INPUT, ConfigUtils.getUserToken()).body().getId().toString();
    }

    @SneakyThrows
    public static void deleteAllTriangles() {
        List<String> triangleList = getAllTringlesIds(getAllTrianglesInfo());
        for (int i = 0; i < triangleList.size(); i++) {
            deleteATriangle(triangleList.get(i), 200);
        }
    }

    public static void createTriangles(Integer amount) {
        if (amount <= 0) log.info("The amount should be more than 0 and less than 10");
        else {
            for (int i = 0; i < amount; i++) {
                createATriangle();
            }
            log.info(String.valueOf(getAllTrianglesInfo().size()));
        }

    }

}
