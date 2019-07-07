package simpleRestTransferAPI.controller.exception;

public class TransferDtoNullException extends CustomException {
    public TransferDtoNullException () {
        super(400, "Request body value is null.");
    }
}
