package edu.mirea.myinvest.domain.dto.security;

import lombok.Builder;

import java.util.List;

@Builder
public record HistoryDataResponse(
        Long id,

        String name,

        String primaryBoardId,

        String shortname,

        String secId,

        List<HistoryLineResponse> data

) {
}
