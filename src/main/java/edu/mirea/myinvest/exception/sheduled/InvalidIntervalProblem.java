package edu.mirea.myinvest.exception.sheduled;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidIntervalProblem extends AbstractThrowableProblem {
    public InvalidIntervalProblem(Integer value) {
        super(
                null,
                "Слишком маленький интервал",
                Status.BAD_REQUEST,
                String.format("Невозможно установить интервал '%s'c, так как он слишком мал", value));
    }
}
