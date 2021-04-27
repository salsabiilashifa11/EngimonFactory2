package Exceptions;

public class OutOfMapException extends Exception {
    private String errorMessage;

    OutOfMapException(String object) {
        super();
        this.errorMessage = object + " Keluar map";
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
