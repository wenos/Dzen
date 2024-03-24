package edu.mirea.vitality.blog.exception.category;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class CategoryNotUniqueTitleProblem extends AbstractThrowableProblem {

    public CategoryNotUniqueTitleProblem (String value) {
        super(
                null,
                "Категория уже существует",
                Status.NOT_FOUND,
                String.format("Категория с названием %s уже существует", value)
        );
    }
}
