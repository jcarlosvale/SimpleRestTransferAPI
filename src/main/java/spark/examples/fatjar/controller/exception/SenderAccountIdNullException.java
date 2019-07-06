package spark.examples.fatjar.controller.exception;

public class SenderAccountIdNullException extends CustomException {
    public SenderAccountIdNullException() {
        super(422, "Invalid Sender Account Id Value");
    }
}
