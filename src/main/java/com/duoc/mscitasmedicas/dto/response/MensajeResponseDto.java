package com.duoc.mscitasmedicas.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensajeResponseDto {

    private String mensaje;

    public MensajeResponseDto() {
    }

    public MensajeResponseDto(String mensaje) {
        this.mensaje = mensaje;
    }

}