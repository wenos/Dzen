package edu.mirea.vitality.blog.exception.user;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserNotFoundProblem extends AbstractThrowableProblem {
    public UserNotFoundProblem() {
        super(
                null,
                "Пользователь не найден",
                Status.NOT_FOUND,
                "Пользователь c указанными параметрами не найден"
        );
    }
}
