package edu.mirea.vitality.blog.exception.comment;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CommentNotFoundProblem extends AbstractThrowableProblem {
    public CommentNotFoundProblem(Long value) {
        super(
                null,
                "Комментарий не найден",
                Status.NOT_FOUND,
                String.format("Комментарий с id %d не найден", value)
        );
    }
}
