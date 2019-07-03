package spark.examples.fatjar.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Account {
    private Long id;
    private BigDecimal balance;
}
