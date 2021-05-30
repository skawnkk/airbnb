package com.codesquad.coco.global.exception;


import com.codesquad.coco.global.exception.auth.AuthException;
import com.codesquad.coco.global.exception.business.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 비즈니스 예외
     **/

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorReason> businessException(BusinessException e) {
        logger.error("비즈니스 에러", e);
        ErrorCode errorCode = e.getErrorCode();
        ErrorReason errorReason = ErrorReason.of(errorCode);
        return new ResponseEntity<>(errorReason, HttpStatus.valueOf(errorCode.getHttpStatus()));
    }

    /**
     * valid 예외
     **/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorReason notNullException(MethodArgumentNotValidException e) {
        logger.error("valid 예외", e);
        return new ErrorReason(ErrorCode.INVALID_INPUT.getMessage());
    }

    /**
     * 인증 관련 예외
     **/

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorReason> notNullException(AuthException e) {
        logger.error("인증관련 에러", e);
        ErrorCode errorCode = e.getErrorCode();
        ErrorReason errorReason = ErrorReason.of(errorCode);
        return new ResponseEntity<>(errorReason, HttpStatus.valueOf(errorCode.getHttpStatus()));
    }

    /**
     * 기타 예외
     **/
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorReason> handleException(Exception e) {
        logger.error("기타 에러", e);
        ErrorReason errorReason = ErrorReason.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorReason, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
