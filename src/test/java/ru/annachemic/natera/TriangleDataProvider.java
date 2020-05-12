package ru.annachemic.natera;

import org.testng.annotations.DataProvider;
import ru.annachemic.natera.rest.RestMethods;

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
                {"", "6;8;10", 200},
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
}
