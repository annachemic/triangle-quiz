package ru.annachemic.natera.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Condition;
import retrofit2.Response;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CommonResponseMatchers {
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
}
