package edu.mirea.myinvest.domain.dto.security;


import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HistoryLineResponse(
        LocalDate date,
        Double open,
        Double low,
        Double high,
        Double close
) {




}
