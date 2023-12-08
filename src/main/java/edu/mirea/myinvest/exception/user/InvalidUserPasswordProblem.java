package edu.mirea.myinvest.exception.user;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidUserPasswordProblem extends AbstractThrowableProblem {
    public InvalidUserPasswordProblem() {
        super(
                null,
                "Некорректные данные пользователя",
                Status.CONFLICT,
                "Неправильный пароль");
    }
}
