package edu.mirea.myinvest.exception.security;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class SecurityNotFoundProblem extends AbstractThrowableProblem {
    public SecurityNotFoundProblem(Long value) {
        super(
                null,
                "Ценная бумага не найдена",
                Status.NOT_FOUND,
                String.format("Ценная бумага с id '%s' не найдена", value));
    }
}
