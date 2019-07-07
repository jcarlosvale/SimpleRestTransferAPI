package simpleRestTransferAPI.exception;

public class SenderAccountIdNullException extends CustomException {
    public SenderAccountIdNullException() {
        super(422, "Invalid Sender Account Id Value");
    }
}
