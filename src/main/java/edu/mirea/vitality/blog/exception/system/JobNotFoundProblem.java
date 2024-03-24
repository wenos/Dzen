package edu.mirea.vitality.blog.exception.system;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class JobNotFoundProblem extends AbstractThrowableProblem {
    public JobNotFoundProblem() {
        super(
                null,
                "Задача не найдена",
                Status.NOT_FOUND,
                "Задача с таким идентификатором не найдена");
    }
}
