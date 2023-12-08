package edu.mirea.myinvest.exception.security;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserSecurityRelationNotFoundProblem extends AbstractThrowableProblem {
    public UserSecurityRelationNotFoundProblem(Long userId, Long securityId) {
        super(
                null,
                "Отношение не найдено",
                Status.NOT_FOUND,
                String.format("Отношение между пользователем с id '%s' и бумагой с id '%s' не найдено",
                        userId, securityId));
    }
}
