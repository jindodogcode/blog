package dev.mkennedy.blog.controller;

import javax.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import dev.mkennedy.blog.model.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        HttpMediaTypeNotSupportedException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        return buildResponseEntity(
            new ApiError(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                builder.substring(0, builder.length() - 2),
                ex
            )
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstrainViolation(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());

        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        NoHandlerFoundException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(
            String.format(
                "Could not find the %s method for URL %s",
                ex.getHttpMethod(),
                ex.getRequestURL()
            )
        );
        apiError.setDebugMessage(ex.getMessage());

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
        javax.persistence.EntityNotFoundException ex
    ) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegerityViolation(
        DataIntegrityViolationException ex,
        WebRequest request
    ) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(
                new ApiError(HttpStatus.CONFLICT,
                    "Database error",
                    ex.getCause()
                )
            );
        }

        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex,
        WebRequest request
    ) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(
            String.format(
                "The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(),
                ex.getValue(),
                ex.getRequiredType().getSimpleName()
            )
        );
        apiError.setDebugMessage(ex.getMessage());

        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
