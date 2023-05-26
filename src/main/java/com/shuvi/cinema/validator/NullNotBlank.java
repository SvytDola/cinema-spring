package com.shuvi.cinema.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Валидатор полей типа {@link String}.
 * <p>filed == null - true</p>
 * <p>"" - false</p>
 * <p>" " - false</p>
 * <p>"text" - true</p>
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NullNotBlankValidator.class)
@Documented
public @interface NullNotBlank {

    String message() default "если поле не null, то должно быть не пустым";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
