package edu.mirea.vitality.blog.exception.file;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.UUID;

public class FileDeleteProblem extends AbstractThrowableProblem {
    public FileDeleteProblem(UUID value) {
        super(
                null,
                "Файл не может быть удален",
                Status.INTERNAL_SERVER_ERROR,
                String.format("Файл с id '%s' не может удален", value));
    }
}