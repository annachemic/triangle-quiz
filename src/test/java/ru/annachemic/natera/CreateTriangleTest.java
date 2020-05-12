package ru.annachemic.natera;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.annachemic.natera.rest.RestMethods.createATriangle;
import static ru.annachemic.natera.rest.RestMethods.deleteATriangle;
import static ru.annachemic.natera.utils.CommonResponseMatchers.*;

@Slf4j
public class CreateTriangleTest {

    String createdTriangleId;

    @BeforeMethod
    public void setUp() {

    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "triangleSeparatorPositiveTests")
    public void createATriangleWithCorrectSeparatorsTest(String separator, String input) {
        Response<TriangleDtoResponse> response =
                createATriangle(separator, input);
        checkStatusCodeStep(response, 200);
        checkNotEmptyBodyStep(response);
        assertThat(response.body()).isNotNull();

        createdTriangleId = response.body().getId().toString();
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "triangleSeparatorNegativeTests")
    public void createATriangleWithIncorrectSeparatorsTest(String separator, String input) {
        Response<TriangleDtoResponse> response = createATriangle(separator, input);
        checkStatusCodeStep(response, 422);
        checkEmptyBodyStep(response);
    }

    @AfterMethod
    public void tearDown() {
        if (StringUtils.isNotBlank(createdTriangleId))
            deleteATriangle(createdTriangleId, 200);
    }

}
