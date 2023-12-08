package edu.mirea.myinvest.exception.security;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserAlreadySubscribeProblem extends AbstractThrowableProblem {
    public UserAlreadySubscribeProblem(Long value) {
        super(
                null,
                "Невозможно подписаться",
                Status.CONFLICT,
                String.format("Пользователь с id '%s' уже подписан на данную ценную бумагу", value));
    }
}
