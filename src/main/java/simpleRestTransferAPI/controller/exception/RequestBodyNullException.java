package simpleRestTransferAPI.controller.exception;

public class RequestBodyNullException extends CustomException {
    public RequestBodyNullException () {
        super(400, "Transfer data is null.");
    }
}
