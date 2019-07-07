package simpleRestTransferAPI.controller.exception;

public class ReceiverAccountIdNullException extends CustomException {

    public ReceiverAccountIdNullException() { //TODO Modificar para Sender e Receiver
        super(422, "Invalid Receiver Account Id Value");
    }
}
