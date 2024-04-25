package com.online.wallet.model.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Transient
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineStatistics {

  private List<String>     labels;
  private List<BigDecimal> values;

}
