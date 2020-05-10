package ru.annachemic.natera.dto.response;

import lombok.Data;

import java.util.UUID;
@Data
public class TriangleDtoResponse {
    UUID id;
    Double firstSide;
    Double secondSide;
    Double thirdSide;
}
