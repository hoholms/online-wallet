package com.endava.wallet.entity;

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
public class CircleStatistics {
    private List<String> categories;
    private List<BigDecimal> values;
}
