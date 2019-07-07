package simpleRestTransferAPI.exception;

public class InvalidAmountException extends CustomException {
    public InvalidAmountException() {
        super(422, "Invalid Amount Value");
    }
}
