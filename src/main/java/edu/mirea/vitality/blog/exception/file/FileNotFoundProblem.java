package edu.mirea.vitality.blog.exception.file;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.UUID;

public class FileNotFoundProblem extends AbstractThrowableProblem {
    public FileNotFoundProblem(UUID value) {
        super(
                null,
                "Файл не найден",
                Status.NOT_FOUND,
                String.format("Файл с id '%s' не найден", value));
    }
}
