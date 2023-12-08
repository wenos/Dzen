package edu.mirea.myinvest.exception.type.security;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class SecurityTypeNotFoundProblem extends AbstractThrowableProblem {
    public SecurityTypeNotFoundProblem(Long value) {
        super(
                null,
                "Тип бумаги не найден",
                Status.NOT_FOUND,
                String.format("Тип бумаги с id '%s' не найден", value));
    }
}
