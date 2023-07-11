package com.online.wallet.model.dto;

import lombok.*;
import org.springframework.security.core.Transient;

import java.math.BigDecimal;
import java.util.List;

@Transient
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineStatistics {
    private List<String> labels;
    private List<BigDecimal> values;
}
