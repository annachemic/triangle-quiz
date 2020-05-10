package ru.annachemic.natera;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.annachemic.natera.dto.request.CreateTriangleDtoRequest;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.services.CreateTriangleService;
import ru.annachemic.natera.utils.RetrofitUtils;

import java.io.IOException;

import static ru.annachemic.natera.utils.CommonResponseMatchers.checkStatusCodeStep;

@Slf4j
public class CreateTriangleTest {

    private CreateTriangleService createTriangleService;

    @BeforeMethod
    public void setUp() throws IOException {
        createTriangleService = RetrofitUtils.getRetrofit()
                .create(CreateTriangleService.class);
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "triangleSeparatorTests")
    public void egyptianTriangleCreationTest(String separator, String input, Integer resultCode) throws IOException {
        retrofit2.Response<TriangleDtoResponse> response =
                createTriangleService.createTriangle(
                        CreateTriangleDtoRequest
                                .builder()
                                .separator(separator)
                                .input(input)
                                .build(), "f78ed3e2-2e7d-4e54-8c41-ffbc715bea1c")
                        .execute();
        //log.info(response.body().toString());
        //checkNotEmptyBodyStep(response);
        //checkStatusCodeStep(response, resultCode);
        //assertThat(response).isEqualTo();


    }

}
