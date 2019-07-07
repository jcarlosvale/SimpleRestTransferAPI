package simpleRestTransferAPI.exception;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class CustomException extends RuntimeException{
    @Expose
    private final int errorCode;
    @Expose
    private final String errorMessage;
}
