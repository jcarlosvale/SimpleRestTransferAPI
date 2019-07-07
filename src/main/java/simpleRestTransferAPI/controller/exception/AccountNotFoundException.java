package simpleRestTransferAPI.controller.exception;

public class AccountNotFoundException extends CustomException {
    public AccountNotFoundException(Long accountId) {
        super(415, "Account not found ID: " + accountId);
    }
}
