package ru.annachemic.natera;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;
import ru.annachemic.natera.rest.RestMethods;

import java.util.List;
import java.util.UUID;

public class TriangleDataProvider {
    @DataProvider
    public static Object[][] getNewTrianglesList() {
        RestMethods.getNewTriangleId();
        return new Object[][]{
                {RestMethods.getAllTringlesIds(RestMethods.getAllTrianglesInfo())}
        };
    }

    @DataProvider
    public Object[][] triangleSeparatorPositiveTests() {
        return new Object[][]{
                {";", "3;4;5"},
                {",", "3,4,5"},
                {" ", "3 4 5"},
                {null, "3;4;5"},
                {"; ", "3; 4; 5"},
        };
    }

    @DataProvider
    public Object[][] triangleSeparatorNegativeTests() {
        return new Object[][]{
                {"", "6;8;10"},
                {"", "6810"},
                {".", "3.4.5"}
        };
    }

    @DataProvider
    public Object[][] triangleWithDoubleSidesTest() {
        return new Object[][]{
                {";", "3.0;4;5"},
                {";", "3;4.3;6"},
                {";", "3;4;5.2"},
                {";", "3;4.1678;5.25"},
                {";", "3.2222222;4.1678;7"},
                {";", "3.0000000004;4;5.002"},
                {";", "0.03;0.04;0.05"},

        };
    }

    @DataProvider
    public Object[][] triangleSidesNegativeTests() {
        return new Object[][]{
                {";", "1;-2;2.5"},
                {";", "-3;4;5"},
                {";", "3;4;-5"},
                {";", "0;0.04;0.05"},
                {";", "0.3;0;0.05"},
                {";", "0.3;0.4;0"},
                {";", "0;0;0"},
                {";", "0;0;0.1"},
                {";", "1;0;0"},
                {";", "0;2;0"},
                {";", "null;4;5"},
                {";", "3;null;5"},
                {";", "3;4;null"},
                {";", ";4;5"},
                {";", "3;;5"},
                {";", "3;4;"},
                {";", "34;5"},
                {";", "345"},
                {";", ""},
                {";", "null"},
        };
    }

    @DataProvider
    public Object[][] triangleCreator() {
        RestMethods.deleteAllTriangles();
        List<TriangleDtoResponse> list = RestMethods.getAllTrianglesInfo();
        if (list.size() == 0)
            list.add(RestMethods.createATriangle(5.5, 4.4, 7.0));
        return new Object[][]{
                {list}
        };
    }

    @DataProvider
    public Object[][] triangleNegativeCreator() {
        RestMethods.deleteAllTriangles();
        List<TriangleDtoResponse> list = RestMethods.getAllTrianglesInfo();
        if (list.size() == 0)
            list.add(RestMethods.createATriangle(-5.5, -4.4, 7.0));
        list.add(RestMethods.createATriangle(-5.5, -4.4, -7.0));
        list.add(RestMethods.createATriangle(0.0, 0.0, 0.0));
        list.add(RestMethods.createATriangle(5.5, 6.0, 0.0));

        return new Object[][]{
                {list.get(0)},
                {list.get(1)},
                {list.get(2)},
                {list.get(3)}
        };
    }

    @DataProvider
    public Object[][] generateNegativeTriangleId() {
        return new Object[][]{
                {UUID.randomUUID().toString(), 404},
                {"5678", 404},
                {"5678.0001", 404},
                {RandomStringUtils.randomAlphabetic(1, 18), 404},
                {" ", 404},
                {"", 405},
        };
    }

    @DataProvider
    public Object[][] triangleForAreaPositiveCreator() {
        RestMethods.deleteAllTriangles();
        return new Object[][]{
                {RestMethods.createATriangle(5.5, 4.4, 7.0)},
                {RestMethods.createATriangle(0.0, 4.4, 7.0)},
                {RestMethods.createATriangle(-5.0, 4.4, 7.0)},
                {RestMethods.createATriangle(-5.0, -4.4, 7.0)},
                {RestMethods.createATriangle(-5.0, -4.4, -7.0)},
        };
    }


    @DataProvider
    public Object[][] triangleForAreaNegativeCreator() {
        return new Object[][]{
                {RestMethods.createATriangle(0.0, 4.4, 7.0)},
                {RestMethods.createATriangle(-5.0, 4.4, 7.0)},
                {RestMethods.createATriangle(-5.0, -4.4, 7.0)},
                {RestMethods.createATriangle(-5.0, -4.4, -7.0)},
        };
    }

    @DataProvider
    public Object[][] nonExistentTriangles() {
        return new Object[][]{
                {UUID.randomUUID().toString(), 404},
                {RandomStringUtils.randomAlphabetic(1, 18), 404},
                {String.valueOf(Math.random()), 404},
                {String.valueOf(Math.round(Math.random() * 10000)), 404},
                {"", 404},
                {" ", 404}
        };
    }
}
