package ru.otus.integration.exception;

public class InvalidPatientDataForCertificateException extends InvalidPatientDataException {

    public InvalidPatientDataForCertificateException() {
        super();
    }

    public InvalidPatientDataForCertificateException(String message) {
        super(message);
    }

    public InvalidPatientDataForCertificateException(String message, Throwable cause) {
        super(message, cause);
    }
}
