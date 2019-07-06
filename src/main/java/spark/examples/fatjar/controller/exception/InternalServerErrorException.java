package spark.examples.fatjar.controller.exception;

public class InternalServerErrorException extends CustomException {
    public InternalServerErrorException() {
        super(500, "Internal Server Error - Unexpected Error");
    }
}
