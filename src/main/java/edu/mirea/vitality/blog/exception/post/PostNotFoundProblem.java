package edu.mirea.vitality.blog.exception.post;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class PostNotFoundProblem extends AbstractThrowableProblem {
    public PostNotFoundProblem(Long value) {
        super(
                null,
                "Пост не найден",
                Status.NOT_FOUND,
                String.format("Пост с id '%s' не найден", value));
    }
}
