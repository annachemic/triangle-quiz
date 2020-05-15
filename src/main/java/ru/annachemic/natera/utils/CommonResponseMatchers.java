package ru.annachemic.natera.utils;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.assertj.core.api.Condition;
import retrofit2.Response;
import ru.annachemic.natera.dto.response.ErrorDto;
import ru.annachemic.natera.dto.response.ResultDto;
import ru.annachemic.natera.dto.response.TriangleDtoResponse;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CommonResponseMatchers {
    static Gson g = new Gson();
    @SneakyThrows
    public static void checkStatusCodeStep(Response<?> response, int expectedCode) {
        assertThat(response.code()).isEqualTo(expectedCode);
        log.info("The response has a right status code");
    }

    public static <T> void checkEmptyBodyStep(Response<T> response) {
        assertThat(response.body()).as("Response without body")
                .is(new Condition<>(r -> isNull(r), "Response has empty body"));
        log.info("Response has empty body");
    }

    public static <T> T checkNotEmptyBodyStep(Response<T> response) {
        assertThat(response.body()).as("Response has not empty body").isNotNull();
        return response.body();
    }

    public static void checkPerimeterStep(Response<ResultDto> response, TriangleDtoResponse triangle) {
        Double result = response.body().getResult();
        assertThat(result).isEqualTo(
                triangle.getFirstSide() + triangle.getSecondSide() + triangle.getThirdSide());
        log.info("Perimeter has been calculated correctly, trianglePerimeter = " + result);
    }

    @SneakyThrows
    public static void checkDefaultErrorStep(Response<?> response, Integer code) {
        checkStatusCodeStep(response, code);
        ResponseBody responseBody = response.errorBody();
        assertThat(responseBody).isNotNull();

        ErrorDto errorDto = g.fromJson(responseBody.string(), ErrorDto.class);
        assertThat(errorDto.getStatus()).isEqualTo(code);
        log.info("Occurred error: " + errorDto.getError() + " with status code " + errorDto.getStatus() + " at " + DateUtils.formatDate(errorDto.getTimestamp()));
    }

    public static void checkArea(TriangleDtoResponse triangle, Double responseResult) {
        assertThat(responseResult).isEqualTo(calculateArea(triangle))
                .as("The area has been calculated successfully");
    }

    public static Double calculateArea(TriangleDtoResponse triangle) {
        Double a = triangle.getFirstSide();
        Double b = triangle.getSecondSide();
        Double c = triangle.getThirdSide();
        Double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

}
