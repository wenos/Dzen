package edu.mirea.myinvest.exception.system;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class IncorrectConfigTypeProblem extends AbstractThrowableProblem {
    public IncorrectConfigTypeProblem() {
        super(
                null,
                "Некорректный тип конфигурации",
                Status.BAD_REQUEST,
                "Значение не соответствует типу конфигурационной единицы"
        );
    }
}