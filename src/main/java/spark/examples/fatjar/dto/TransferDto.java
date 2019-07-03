package spark.examples.fatjar.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private Long originAccountId;
    private Long destinyAccountId;
    private BigDecimal amount;
}
