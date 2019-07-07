package simpleRestTransferAPI.exception;

public class ContentTypeNotAcceptedException extends CustomException {
    public ContentTypeNotAcceptedException () {
        super(415, "Content-type not accepted");
    }
}
