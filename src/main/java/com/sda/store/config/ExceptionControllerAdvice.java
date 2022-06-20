package com.sda.store.config;

import com.sda.store.exception.ResourceAlreadyPresentInDatabase;
import com.sda.store.exception.ResourceNotFoundInDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = {ResourceNotFoundInDatabase.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundInDb(ResourceNotFoundInDatabase resourceException){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(resourceException.getMessage());
        return errorMessage;
    }

    @ExceptionHandler(value = {ResourceAlreadyPresentInDatabase.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceAlreadyExistsException(ResourceAlreadyPresentInDatabase presentInDatabaseException){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(presentInDatabaseException.getMessage());
        return errorMessage;
    }

}
