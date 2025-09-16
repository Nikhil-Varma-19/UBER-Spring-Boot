package com.example.uber.uber_backend.advices;

import com.example.uber.uber_backend.dtos.ErrorLoggerDto;
import com.example.uber.uber_backend.exceptions.ResourceNotFound;
import com.example.uber.uber_backend.exceptions.RuntimeConflictException;
import com.example.uber.uber_backend.services.LoggerService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private LoggerService loggerService;

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFound exc){
        ApiError apiError=ApiError.builder().status(HttpStatus.NOT_FOUND).message(exc.getMessage()).build();
        return buildErrorApiResponse(apiError);
    }
    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeConflict(RuntimeConflictException exc){
        ApiError apiError=ApiError.builder().status(HttpStatus.CONFLICT).message(exc.getMessage()).build();
        return buildErrorApiResponse(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> accessDenied(AccessDeniedException auth){
        ApiError apiError=ApiError.builder().status(HttpStatus.FORBIDDEN).message("Access Denied.Contact Admin").build();
        return buildErrorApiResponse(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> authError(AuthenticationException auth){
        ApiError apiError=ApiError.builder().status(HttpStatus.UNAUTHORIZED).message("Invalid username or password.").build();
        return buildErrorApiResponse(apiError);

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> validationError(MethodArgumentNotValidException exception){
        List<String> errors=exception.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        ApiError apiError=ApiError.builder().status(HttpStatus.BAD_REQUEST).message(errors.getFirst()).build();
        return  buildErrorApiResponse(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> jwtError(JwtException  jwtException){
        ApiError apiError=ApiError.builder().status(HttpStatus.UNAUTHORIZED).message("Auth Failed").build();
        return buildErrorApiResponse(apiError);
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ApiResponse<?>> handleGlobalError(Exception ex, HttpServletRequest request){
        System.out.println(ex.getMessage());
        ErrorLoggerDto errorLoggerDto=new ErrorLoggerDto();
        errorLoggerDto.setMessage(ex.getMessage());
        errorLoggerDto.setMethod(request.getMethod());
        errorLoggerDto.setUrl(request.getRequestURI());
        if(request instanceof ContentCachingRequestWrapper wrapper){
            // ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            byte[] content= wrapper.getContentAsByteArray();
            String body=new String(content, StandardCharsets.UTF_8);
            errorLoggerDto.setBody(body);
        }else {
            errorLoggerDto.setBody(null);
        }
        loggerService.createErrorLog(errorLoggerDto);

        ApiError apiError=ApiError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Something went wrong").build();
        return  buildErrorApiResponse(apiError);
    }

    private  ResponseEntity<ApiResponse<?>> buildErrorApiResponse(ApiError error){
    return  new ResponseEntity<>(new ApiResponse<>(error),error.getStatus());
    }
}
