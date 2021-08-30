package com.audit.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.audit.PvApplication;

@ControllerAdvice
@RestController
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webReq) {

		ExceptionResponse exceptResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				webReq.getDescription(false));
		return new ResponseEntity<Object>(exceptResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest webReq) {

		ExceptionResponse exceptResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				webReq.getDescription(false));
		return new ResponseEntity<Object>(exceptResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnAutherizedException.class)
	public final ResponseEntity<Object> handleUnAuthorizedException(Exception ex, WebRequest webReq) {
		ExceptionResponse exceptionResp = new ExceptionResponse(new Date(), ex.getMessage(),
				webReq.getDescription(false));
		logger.error("UnAuthorized Exception {}, at {}",ex.getMessage(),exceptionResp.getExceptionTime());
		return new ResponseEntity<Object>(exceptionResp, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<Object> handleBadRequestException(Exception ex, WebRequest webReq) {
		ExceptionResponse exceptionResp = new ExceptionResponse(new Date(), ex.getMessage(),
				webReq.getDescription(false));
		logger.error("BadRequest Exception {}, at {}",ex.getMessage(),exceptionResp.getExceptionTime());
		return new ResponseEntity<Object>(exceptionResp, HttpStatus.BAD_REQUEST);
	}

	public final ResponseEntity<Object> handleAuditException(Exception ex, WebRequest webReq) {
		ExceptionResponse exceptionResp = new ExceptionResponse(new Date(), ex.getMessage(),
				webReq.getDescription(false));
		return new ResponseEntity<Object>(exceptionResp, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
