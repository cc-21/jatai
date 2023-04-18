package Exceptions;

public class IllegalDeclarationError extends RuntimeException {
    public IllegalDeclarationError(String errorMessage) {
        super(errorMessage);
    }
}
