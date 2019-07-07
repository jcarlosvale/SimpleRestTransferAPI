package simpleRestTransferAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private Long senderAccountId;
    private Long receiverAccountId;
    private BigDecimal amount;
}
