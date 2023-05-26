package com.shuvi.cinema.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор полей типа {@link String}.
 * <p>filed == null - true</p>
 * <p>"" - false</p>
 * <p>" " - false</p>
 * <p>"text" - true</p>
 */
public class NullNotBlankValidator implements ConstraintValidator<NullNotBlank, String> {
    @Override
    public void initialize(NullNotBlank constraint) {
        //Нет необходимости в заполнении
    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return true;
        }
        return StringUtils.hasText(obj);
    }
}
