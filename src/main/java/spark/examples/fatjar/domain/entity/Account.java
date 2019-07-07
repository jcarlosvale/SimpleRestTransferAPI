package spark.examples.fatjar.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    private BigDecimal balance;

    public Account(BigDecimal balance){
        this.balance = balance;
    }
}
