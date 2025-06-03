package com.nettoya.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RatingSummaryResponse {
    private Long cleanerId;
    private Double promedio;
    private Integer totalRatings;
}

