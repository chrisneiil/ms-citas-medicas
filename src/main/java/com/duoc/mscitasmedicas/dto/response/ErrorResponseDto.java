package com.duoc.mscitasmedicas.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponseDto {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Object details;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(LocalDateTime timestamp, int status, String error, Object details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details;
    }

}