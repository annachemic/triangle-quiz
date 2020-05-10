package ru.annachemic.natera.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CommonResponseMatchers {
    @SneakyThrows
    public static void checkStatusCodeStep(Response<?> response, int expectedCode) {
        assertThat(response.code()).isEqualTo(expectedCode);
        log.info("The response has a right status code");
    }
    public static void checkNotEmptyBodyStep(Response response) {
        assertThat(response.body()).isNotNull();
        log.info("Response has not empty body");
    }
}
