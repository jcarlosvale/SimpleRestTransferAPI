package simpleRestTransferAPI.exception;

public class AccountNotFoundException extends CustomException {
    public AccountNotFoundException(Long accountId) {
        super(422, "Account not found ID: " + accountId);
    }
}
