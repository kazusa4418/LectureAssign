public class FailedToAssignException extends RuntimeException {
    private int errorCode;

    FailedToAssignException(int errorCode) {
        super("failed to assign.");
    }

    public int getErrorCode() {
        return errorCode;
    }
}
