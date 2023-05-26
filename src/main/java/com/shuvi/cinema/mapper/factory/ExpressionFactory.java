package com.shuvi.cinema.mapper.factory;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.function.Function;

public abstract class ExpressionFactory {

    public <T> BooleanExpression andExpressionIgnoreNull(Function<T, BooleanExpression> getterField,
                                                         T expectedValue,
                                                         BooleanExpression expression) {
        if (expectedValue != null) {
            return expression.and(getterField.apply(expectedValue));
        }
        return expression;
    }
}