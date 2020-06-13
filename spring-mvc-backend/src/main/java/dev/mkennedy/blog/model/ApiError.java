package dev.mkennedy.blog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ApiError {
    
    private HttpStatus status;
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this(status);
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this(status);
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }

        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
            fieldError.getObjectName(),
            fieldError.getField(),
            fieldError.getRejectedValue(),
            fieldError.getDefaultMessage()
        );
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
            objectError.getObjectName(),
            objectError.getDefaultMessage()
        );
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
            cv.getRootBeanClass().getSimpleName(),
            ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
            cv.getInvalidValue(),
            cv.getMessage()
        );
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constrainViolations) {
        constrainViolations.forEach(this::addValidationError);
    }

    public HttpStatus getStatus() {
      return status;
    }

    public void setStatus(HttpStatus status) {
      this.status = status;
    }

    public LocalDateTime getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getDebugMessage() {
      return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
      this.debugMessage = debugMessage;
    }

    public List<ApiSubError> getSubErrors() {
      return subErrors;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
      this.subErrors = subErrors;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
          + ((debugMessage == null) ? 0 : debugMessage.hashCode());
      result = prime * result + ((message == null) ? 0 : message.hashCode());
      result = prime * result + ((status == null) ? 0 : status.hashCode());
      result =
          prime * result + ((subErrors == null) ? 0 : subErrors.hashCode());
      result =
          prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ApiError other = (ApiError) obj;
      if (debugMessage == null) {
        if (other.debugMessage != null)
          return false;
      } else if (!debugMessage.equals(other.debugMessage))
        return false;
      if (message == null) {
        if (other.message != null)
          return false;
      } else if (!message.equals(other.message))
        return false;
      if (status != other.status)
        return false;
      if (subErrors == null) {
        if (other.subErrors != null)
          return false;
      } else if (!subErrors.equals(other.subErrors))
        return false;
      if (timestamp == null) {
        if (other.timestamp != null)
          return false;
      } else if (!timestamp.equals(other.timestamp))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "ApiError [debugMessage=" + debugMessage + ", message=" + message
          + ", status=" + status + ", subErrors=" + subErrors + ", timestamp="
          + timestamp + "]";
    }
}
