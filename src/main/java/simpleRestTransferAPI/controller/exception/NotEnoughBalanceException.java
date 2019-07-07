package simpleRestTransferAPI.controller.exception;

public class NotEnoughBalanceException extends CustomException {
    public NotEnoughBalanceException() {
        super(422, "Not Enough Balance To Transfer");
    }
}
