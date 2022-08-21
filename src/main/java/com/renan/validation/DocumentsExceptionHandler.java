package com.renan.validation;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.renan.DocumentsFetchException;

@ControllerAdvice
public class DocumentsExceptionHandler
{
	@ExceptionHandler(DocumentsFetchException.class)
	public ResponseEntity handleException(DocumentsFetchException e) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body("Error fetching documents: " + e.getMessage());
	}

	@ExceptionHandler(ConversionFailedException.class)
	public ResponseEntity handleException(ConversionFailedException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body("Invalid arguments" + e.getMessage());
	}
}
