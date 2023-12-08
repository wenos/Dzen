package edu.mirea.myinvest.exception.user;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserDeletedProblem extends AbstractThrowableProblem {
    public UserDeletedProblem() {
        super(
                null,
                "Пользователь удален",
                Status.NOT_FOUND,
                "Пользователь которого вы пытаетесь найти был удален"
        );
    }
}
