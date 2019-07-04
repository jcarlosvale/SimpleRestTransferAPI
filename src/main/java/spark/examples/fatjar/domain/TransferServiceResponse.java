package spark.examples.fatjar.domain;

import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransferServiceResponse {
    private final StatusResponse status;
    private String message;
    private JsonElement data;

    public TransferServiceResponse(StatusResponse statusResponse, String message) {
        this(statusResponse);
        this.message = message;
    }
}
