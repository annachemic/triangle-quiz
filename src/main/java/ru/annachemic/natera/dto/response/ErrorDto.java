package ru.annachemic.natera.dto.response;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ErrorDto {
    public Long timestamp;
    public Integer status;
    public String error;
    public String message;
    public String path;
}
