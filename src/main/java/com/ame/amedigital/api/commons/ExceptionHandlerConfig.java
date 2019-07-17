package com.ame.amedigital.api.commons;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ame.amedigital.api.commons.ResponseObject.ResponseError;

@ControllerAdvice
@Component("serviceExceptionHandlerConfig")
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler{
	
	private static final Log LOG = LogFactory.getLog(ExceptionHandlerConfig.class);
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseObject<Object> handle(HttpServletRequest req, Exception e) {
        LOG.error("Handling RestApiException", e);
        final ResponseError responseError = buildError(e);
        final ResponseObject<Object> responseObject = new ResponseBuilder<>().withError(responseError).build();
        return responseObject;
    }
	
	private ResponseError buildError(Exception e) {
        final ResponseError responseError = new ResponseError();
        responseError.setDescription(e.getMessage());
        return responseError;
    }
	
}
