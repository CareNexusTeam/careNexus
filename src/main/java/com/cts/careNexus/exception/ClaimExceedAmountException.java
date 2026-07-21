package com.cts.careNexus.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClaimExceedAmountException extends RuntimeException {

    public ClaimExceedAmountException(String message)
    {
        super(message);
    }

}
