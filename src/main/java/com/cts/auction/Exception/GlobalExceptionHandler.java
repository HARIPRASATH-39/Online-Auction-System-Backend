package com.cts.auction.Exception;

import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException){
	return buildErrorResponse(userNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
}

@ExceptionHandler(ProductNotFoundException.class)
public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
	return buildErrorResponse(productNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
}

@ExceptionHandler(AuctionNotFoundException.class)
public ResponseEntity<Object> handleAuctionNotFoundException(AuctionNotFoundException auctionNotFoundException){
	return buildErrorResponse(auctionNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    
    
}

@ExceptionHandler(RuntimeException.class)
public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
}


private ResponseEntity<Object> buildErrorResponse(String message,HttpStatus status){
	Map<String,Object>response=new HashMap<>();
	response.put("message", message);
	response.put("status", status.value());
	return new ResponseEntity<>(response,status);
}  

private Map<String, List<String>> getErrorsMap(List<String> errors) {
    Map<String, List<String>> errorResponse = new HashMap<>();
    errorResponse.put("errors", errors);
    return errorResponse;
}


}
