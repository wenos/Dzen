package edu.mirea.vitality.blog.exception.user;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserNotUniqueUsernameProblem extends AbstractThrowableProblem {
    public UserNotUniqueUsernameProblem(String value) {
        super(
                null,
                "Пользователь с таким именем уже зарегистрирован",
                Status.CONFLICT,
                String.format("Пользователь с именем '%s' уже зарегистрирован", value));
    }
}
