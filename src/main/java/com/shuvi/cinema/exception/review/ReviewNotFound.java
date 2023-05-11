package com.shuvi.cinema.exception.review;

import com.shuvi.cinema.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Shuvi
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ReviewNotFound extends BaseException {
}
