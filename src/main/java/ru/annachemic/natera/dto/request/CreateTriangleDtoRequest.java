package ru.annachemic.natera.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateTriangleDtoRequest {
    String separator;
    String input;
}
