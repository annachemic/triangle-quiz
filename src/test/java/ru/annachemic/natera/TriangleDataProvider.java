package ru.annachemic.natera;

import org.testng.annotations.DataProvider;
import ru.annachemic.natera.rest.RestMethods;

public class TriangleDataProvider {
    @DataProvider
    public Object[][] triangleSeparatorTests() {
        return new Object[][]{
                {";", "3;4;5", 200},
                {",", "3,4,5", 200},
                {" ", "3 4 5", 200},
                {"", "3;4;5", 200},
                {null, "3;4;5", 200},
                {"; ", "3;4;5", 200},
                {".", "3.4.5", 422}
        };
    }


    @DataProvider
    public static Object[][] getTrianglesList() {
        return new Object[][]{
                {RestMethods.getAllTringlesIds(RestMethods.getAllTrianglesInfo())}
        };
    }
}
