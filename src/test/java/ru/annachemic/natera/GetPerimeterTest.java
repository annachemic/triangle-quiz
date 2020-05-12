package ru.annachemic.natera;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.annachemic.natera.dto.response.PerimeterDto;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.services.GetPerimeterService;
import ru.annachemic.natera.utils.ConfigUtils;
import ru.annachemic.natera.utils.RetrofitUtils;

import java.util.List;

import static ru.annachemic.natera.utils.CommonResponseMatchers.*;

@Slf4j
public class GetPerimeterTest {

    GetPerimeterService getPerimeterService;
    TriangleDtoResponse triangle;
    String triangleId;

    @SneakyThrows
    @BeforeMethod
    public void setUp() {
        getPerimeterService = RetrofitUtils.getRetrofit().create(GetPerimeterService.class);
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "triangleCreator")
    @SneakyThrows
    public void getPerimeterTest(List<TriangleDtoResponse> list) {
        triangle = list.get(0);
        triangleId = triangle.getId().toString();
        retrofit2.Response<PerimeterDto> response =
                getPerimeterService.getAPerimeter(
                        triangleId, ConfigUtils.getUserToken())
                        .execute();
        checkNotEmptyBodyStep(response);
        checkStatusCodeStep(response, 200);
        checkPerimeterStep(response, triangle);
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "triangleNegativeCreator")
    @SneakyThrows
    public void getPerimeterWithNegativeSideValuesTest(TriangleDtoResponse triangle) {
        triangleId = triangle.getId().toString();
        retrofit2.Response<PerimeterDto> response =
                getPerimeterService.getAPerimeter(
                        triangleId, ConfigUtils.getUserToken())
                        .execute();
        checkDefaultErrorStep(response, 422);
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "triangleCreator")
    @SneakyThrows
    public void getPerimeterWithoutAuthTest(List<TriangleDtoResponse> list) {
        triangleId = list.get(0).getId().toString();
        retrofit2.Response<PerimeterDto> response =
                getPerimeterService.getAPerimeter(
                        triangleId, "")
                        .execute();
        checkDefaultErrorStep(response, 401);
    }
}
