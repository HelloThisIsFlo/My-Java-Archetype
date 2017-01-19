package com.professionalbeginner.presentation.exception;

import com.professionalbeginner.domain.core.book.IllegalReviewException;
import com.professionalbeginner.domain.interfacelayer.repository.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kempenich Florian
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseBody
    public ErrorInfo handleExistingReview(HttpServletRequest request, BookNotFoundException ex) {
        LOG.error("Tried to get book, but id not found! --> id={}", ex.getSearchedId());
        return new ErrorInfo(request.getRequestURL().toString(), ex);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalReviewException.class)
    @ResponseBody
    public ErrorInfo handleExistingReview(HttpServletRequest request, IllegalReviewException ex) {
        LOG.error("Tried to get book, but id not found! --> review={}", ex.getReview());
        return new ErrorInfo(request.getRequestURL().toString(), ex);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorInfo handleExistingReview(HttpServletRequest request, IllegalArgumentException ex) {
        LOG.error("IllegalArgument exception:", ex);
        return new ErrorInfo(request.getRequestURL().toString(), ex);
    }

}
