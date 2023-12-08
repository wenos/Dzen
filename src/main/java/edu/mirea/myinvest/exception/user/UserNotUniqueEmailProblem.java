package edu.mirea.myinvest.exception.user;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserNotUniqueEmailProblem extends AbstractThrowableProblem {
    public UserNotUniqueEmailProblem(String value) {
        super(
                null,
                "Пользователь с таким адресом уже зарегистрирован",
                Status.CONFLICT,
                String.format("Пользователь с адресом электронной почты '%s' уже зарегистрирован", value));
    }
}
