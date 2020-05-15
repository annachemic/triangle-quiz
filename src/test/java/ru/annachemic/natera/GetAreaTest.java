package ru.annachemic.natera;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.annachemic.natera.dto.response.ResultDto;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.rest.RestMethods;
import ru.annachemic.natera.services.GetAreaService;
import ru.annachemic.natera.utils.ConfigUtils;
import ru.annachemic.natera.utils.RetrofitUtils;

import java.util.Objects;

import static ru.annachemic.natera.rest.RestMethods.getNewTriangleId;
import static ru.annachemic.natera.utils.CommonResponseMatchers.*;

@Slf4j
public class GetAreaTest {
    GetAreaService getAreaService;
    String triangleId;

    @SneakyThrows
    @BeforeMethod
    public void setUp() {
        getAreaService = RetrofitUtils.getRetrofit()
                .create(GetAreaService.class);
        triangleId = getNewTriangleId();
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "triangleForAreaPositiveCreator")
    @SneakyThrows
    public void getTriangleAreaTest(TriangleDtoResponse triangle) {
        String triangleId = triangle.getId().toString();
        retrofit2.Response<ResultDto> response =
                getAreaService.getTriangleArea(
                        triangleId, ConfigUtils.getUserToken())
                        .execute();

        checkNotEmptyBodyStep(response);
        checkStatusCodeStep(response, 200);
        checkArea(triangle, Objects.requireNonNull(response.body().getResult()));
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "triangleForAreaNegativeCreator")
    @SneakyThrows
    public void getTriangleAreaWithNegativeSideValuesTest(TriangleDtoResponse triangle) {
        String triangleId = triangle.getId().toString();
        retrofit2.Response<ResultDto> response =
                getAreaService.getTriangleArea(
                        triangleId, ConfigUtils.getUserToken())
                        .execute();

        checkDefaultErrorStep(response, 422);
        checkStatusCodeStep(response, 422);
    }


    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "nonExistentTriangles")
    @SneakyThrows
    public void getTriangleAreaWithNonExistentTriangleIdTest(String triangleId, Integer code) {
        retrofit2.Response<ResultDto> response =
                getAreaService.getTriangleArea(
                        triangleId, ConfigUtils.getUserToken())
                        .execute();

        checkDefaultErrorStep(response, code);

    }

    @Test
    @SneakyThrows
    public void getTriangleAreaWithoutAuthTest() {
        retrofit2.Response<ResultDto> response =
                getAreaService.getTriangleArea(
                        triangleId, "")
                        .execute();

        checkDefaultErrorStep(response, 401);
    }


    @AfterMethod
    public void tearDown() {
        RestMethods.deleteAllTriangles();
    }
}
