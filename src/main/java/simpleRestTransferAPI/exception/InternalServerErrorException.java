package simpleRestTransferAPI.exception;

public class InternalServerErrorException extends CustomException {
    public InternalServerErrorException() {
        super(500, "Internal Server Error - Unexpected Error");
    }
}
