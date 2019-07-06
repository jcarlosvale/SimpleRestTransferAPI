package spark.examples.fatjar.controller.exception;

public class InvalidAmountException extends CustomException {
    public InvalidAmountException() {
        super(422, "Invalid Amount Value");
    }
}
