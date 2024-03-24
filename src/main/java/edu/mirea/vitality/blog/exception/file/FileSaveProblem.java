package edu.mirea.vitality.blog.exception.file;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class FileSaveProblem extends AbstractThrowableProblem {
    public FileSaveProblem() {
        super(
                null,
                "Файл не сохранен",
                Status.INTERNAL_SERVER_ERROR,
                "Файл не может быть сохранен");
    }
}
