package edu.mirea.vitality.blog.exception.category;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CategoryNotFoundProblem extends AbstractThrowableProblem {

    public CategoryNotFoundProblem(Long value) {
        super(
                null,
                "Категория не найдена",
                Status.NOT_FOUND,
                String.format("Категория с id %s не найдена", value)

        );
    }
}
