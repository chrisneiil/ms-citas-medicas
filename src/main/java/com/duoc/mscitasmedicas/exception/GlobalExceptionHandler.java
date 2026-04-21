package com.duoc.mscitasmedicas.exception;

import com.duoc.mscitasmedicas.dto.response.ErrorResponseDto;
import com.duoc.mscitasmedicas.dto.response.MensajeResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex) {
        log.error("Error de validación", ex);

        Map<String, String> errores = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Datos de entrada inválidos",
                errores
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<MensajeResponseDto> handleNotFound(NoSuchElementException ex) {
        log.error("Recurso no encontrado", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MensajeResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensajeResponseDto> handleBusiness(IllegalArgumentException ex) {
        log.error("Error de negocio", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MensajeResponseDto(ex.getMessage()));
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<MensajeResponseDto> handleFormatErrors(Exception ex) {
        log.error("Error de formato en la solicitud", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MensajeResponseDto("Formato inválido en la solicitud. Para fechas usa yyyy-MM-dd."));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensajeResponseDto> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Violación de restricción en base de datos", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MensajeResponseDto("Violación de restricción en base de datos"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneral(Exception ex) {
        log.error("Error interno del servidor", ex);

        ErrorResponseDto response = new ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}