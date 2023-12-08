package edu.mirea.myinvest.exception.user;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidUserDataProblem extends AbstractThrowableProblem {
    public InvalidUserDataProblem() {
        super(
                null,
                "Некорректные данные пользователя",
                Status.CONFLICT,
                "Неправильные имя пользователя или пароль");
    }
}
