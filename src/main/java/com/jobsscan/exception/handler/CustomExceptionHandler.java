package com.jobsscan.exception.handler;

import com.jobsscan.exception.GeocodeException;
import com.jobsscan.exception.ParseDataException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {GeocodeException.class})
    public ResponseEntity<Object> handleGeocodeException(GeocodeException ex){

        ErrorMessage errorMessage = new ErrorMessage(new Date() + StringUtils.SPACE + ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ParseDataException.class})
    public ResponseEntity<Object> handleParseDataException(GeocodeException ex){

        ErrorMessage errorMessage = new ErrorMessage(new Date() + StringUtils.SPACE + ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherException(Exception ex) {

        ErrorMessage errorMessage = new ErrorMessage(new Date() + StringUtils.SPACE + ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
