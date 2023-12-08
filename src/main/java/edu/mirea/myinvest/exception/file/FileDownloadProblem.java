package edu.mirea.myinvest.exception.file;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.UUID;

public class FileDownloadProblem extends AbstractThrowableProblem {
    public FileDownloadProblem(UUID value) {
        super(
                null,
                "Файл не может быть загружен",
                Status.INTERNAL_SERVER_ERROR,
                String.format("Файл с id '%s' не может быть загружен", value));
    }
}
