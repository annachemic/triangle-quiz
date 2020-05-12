package ru.annachemic.natera;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.rest.RestMethods;
import ru.annachemic.natera.services.GetTriangleInfoService;
import ru.annachemic.natera.utils.RetrofitUtils;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.annachemic.natera.rest.RestMethods.createATriangle;
import static ru.annachemic.natera.rest.RestMethods.deleteATriangle;

@Slf4j
public class GetTriangleInfoTests {
    TriangleDtoResponse triangle;
    GetTriangleInfoService getTriangleInfoService;

    @BeforeMethod
    public void setUp() throws IOException {
        triangle = createATriangle();
        getTriangleInfoService = RetrofitUtils.getRetrofit()
                .create(GetTriangleInfoService.class);
    }

    @Test
    public void getAllTrianglesInfoTest() {
        List<TriangleDtoResponse> trianglesList = RestMethods.getAllTrianglesInfo();
        assertThat(trianglesList.get(0)).isNotNull();
        assertThat(trianglesList).containsAnyOf(triangle);
        log.info(RestMethods.getAllTringlesIds(trianglesList).toString());
        log.info(String.valueOf(trianglesList.size()));

    }

    @AfterMethod
    public void tearDown() {
        deleteATriangle(triangle.getId().toString(), 200);
    }
}
