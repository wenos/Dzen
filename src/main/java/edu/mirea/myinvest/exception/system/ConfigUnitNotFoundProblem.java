package edu.mirea.myinvest.exception.system;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ConfigUnitNotFoundProblem extends AbstractThrowableProblem {
    public ConfigUnitNotFoundProblem(String id) {
        super(
                null,
                "Конфигурационная единица не найдена",
                Status.NOT_FOUND,
                String.format("Конфигурационная единица с идентификатором %s не найдена", id));
    }
}
