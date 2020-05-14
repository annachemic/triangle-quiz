package ru.annachemic.natera;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.rest.RestMethods;
import ru.annachemic.natera.services.GetTriangleInfoService;
import ru.annachemic.natera.utils.ConfigUtils;
import ru.annachemic.natera.utils.RetrofitUtils;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.annachemic.natera.rest.RestMethods.createATriangle;
import static ru.annachemic.natera.rest.RestMethods.deleteATriangle;
import static ru.annachemic.natera.utils.CommonResponseMatchers.*;

@Slf4j
public class GetTriangleInfoTests {
    TriangleDtoResponse triangle;
    GetTriangleInfoService getTriangleInfoService;
    String triangleId;
    String userKey;

    @SneakyThrows
    @BeforeMethod
    public void setUp() {
        triangle = createATriangle();
        triangleId = triangle.getId().toString();
        log.info("Created new triangle with id " + triangleId);
        getTriangleInfoService = RetrofitUtils.getRetrofit()
                .create(GetTriangleInfoService.class);
        userKey = ConfigUtils.getUserToken();
    }

    @Test
    public void getAllTrianglesInfoTest() {
        List<TriangleDtoResponse> trianglesList = RestMethods.getAllTrianglesInfo();
        assertThat(trianglesList.get(0)).isNotNull();
        assertThat(trianglesList).containsAnyOf(triangle);
        log.info(RestMethods.getAllTringlesIds(trianglesList).toString());
        log.info("Triangle list size: " + trianglesList.size());
    }

    @SneakyThrows
    @Test
    public void getAllTrianglesInfoWithoutAuthTest() {
        Response<List<TriangleDtoResponse>> response =
                getTriangleInfoService
                        .getTriangleList("")
                        .execute();
        checkDefaultErrorStep(response, 401);
    }

    @SneakyThrows
    @Test
    public void getAnExistingTriangleTest() {
        retrofit2.Response<TriangleDtoResponse> response =
                getTriangleInfoService
                        .getTriangleInfoById(
                                triangleId, userKey)
                        .execute();
        checkNotEmptyBodyStep(response);
        checkStatusCodeStep(response, 200);
        assertThat(Objects.requireNonNull(response.body().getId().toString())).isNotNull();
    }

    @SneakyThrows
    @Test(dataProviderClass = TriangleDataProvider.class,
            dataProvider = "generateNegativeTriangleId")
    public void getNonExistingTriangleTest(String triangleId, Integer code) {
        retrofit2.Response<TriangleDtoResponse> response =
                getTriangleInfoService
                        .getTriangleInfoById(
                                triangleId, userKey)
                        .execute();
        checkDefaultErrorStep(response, code);
    }

    @SneakyThrows
    @Test
    public void getAnExistingTriangleWithoutAuthTest() {
        retrofit2.Response<TriangleDtoResponse> response =
                getTriangleInfoService.getTriangleInfoById(
                        triangleId, "")
                        .execute();
        checkDefaultErrorStep(response, 401);
    }

    @AfterMethod
    public void tearDown() {
        deleteATriangle(triangle.getId().toString(), 200);
    }
}
