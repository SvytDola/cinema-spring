package com.shuvi.cinema.mapper.factory;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.shuvi.cinema.entity.QCinemaEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CinemaExpressionFactory extends ExpressionFactory {

    public BooleanExpression getBooleanExp(@Nullable List<String> genres) {
        QCinemaEntity qCinemaEntity = QCinemaEntity.cinemaEntity;
        BooleanExpression exp = qCinemaEntity.id.isNotNull();
        exp = andExpressionIgnoreNull(qCinemaEntity.genres.any().name::in, genres, exp);
        return exp;
    }
}
