package com.test.boot.helloJPA;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public @ResponseBody CustomResponse springHandlePersonNotFound(
            HttpServletResponse response,
            PersonNotFoundException e
    ) throws IOException {
        response.setStatus(404);
        return new CustomResponse(
                false,
                e.getLocalizedMessage()
        );
    }

    @ExceptionHandler(PhoneNotFoundException.class)
    public @ResponseBody CustomResponse springHandlePhoneNotFound(
            HttpServletResponse response,
            PhoneNotFoundException e
    ) throws IOException {
        response.setStatus(404);
        return new CustomResponse(
                false,
                e.getLocalizedMessage()
        );
    }

}
