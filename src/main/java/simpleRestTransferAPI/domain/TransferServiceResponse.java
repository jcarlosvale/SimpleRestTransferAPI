package simpleRestTransferAPI.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransferServiceResponse {
    private final int code;
    private final String message;
}
