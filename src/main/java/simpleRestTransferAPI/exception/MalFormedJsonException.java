package simpleRestTransferAPI.exception;

public class MalFormedJsonException extends CustomException {
    public MalFormedJsonException() {
        super(400, "Invalid Json value");
    }
}
