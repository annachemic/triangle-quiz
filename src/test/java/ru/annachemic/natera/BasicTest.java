package ru.annachemic.natera;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.utils.CommonResponseMatchers;

import static ru.annachemic.natera.rest.RestMethods.*;

public class BasicTest {

    @BeforeMethod
    public void setUp() {
//        deleteAllTriangles();

    }

    @Test
    public void createMoreThanTenTriangles() {
        createTriangles(10);
        Response<TriangleDtoResponse> response =
                createATriangle(null, "6;8;10");
        CommonResponseMatchers.checkDefaultErrorStep(response, 422);
    }

    @AfterMethod
    public void tearDown() {
        deleteAllTriangles();
    }
}
