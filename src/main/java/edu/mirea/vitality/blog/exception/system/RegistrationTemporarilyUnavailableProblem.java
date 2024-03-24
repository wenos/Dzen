package edu.mirea.vitality.blog.exception.system;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class RegistrationTemporarilyUnavailableProblem extends AbstractThrowableProblem {
    public RegistrationTemporarilyUnavailableProblem() {
        super(
                null,
                "Регистрация временно недоступна",
                Status.SERVICE_UNAVAILABLE,
                "Попробуйте повторить попытку позже");
    }
}
