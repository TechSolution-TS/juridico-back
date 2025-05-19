package com.ts.juridico.infrastructure.exception;

import com.ts.juridico.application.dto.response.ErrorDto;
//import com.ts.juridico.packages.exception.ErrorResponse;
//import com.ts.juridico.packages.exception.ResourceNotFoundException;
//import com.ts.juridico.packages.exception.TimeSlotNotAvailableException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorDto> handleStorage(FileStorageException ex) {
        ErrorDto err = new ErrorDto("UPLOAD_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        ErrorResponse error = ErrorResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.NOT_FOUND.value())
//                .error("Not Found")
//                .message(ex.getMessage())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//    }
//
//    @ExceptionHandler(TimeSlotNotAvailableException.class)
//    public ResponseEntity<ErrorResponse> handleTimeSlotNotAvailableException(TimeSlotNotAvailableException ex) {
//        ErrorResponse error = ErrorResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.CONFLICT.value())
//                .error("Conflict")
//                .message(ex.getMessage())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        // Extrair erros de validação
//        Map<String, String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .collect(Collectors.toMap(
//                        FieldError::getField,
//                        DefaultMessageSourceResolvable::getDefaultMessage,
//                        (existing, replacement) -> existing + "; " + replacement
//                ));
//
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.BAD_REQUEST.value())
//                .error("Validation Error")
//                .message("Campos inválidos na requisição")
//                .details(errors)
//                .build();
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
//        ErrorResponse error = ErrorResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .error("Internal Server Error")
//                .message("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.")
//                .build();
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
}
