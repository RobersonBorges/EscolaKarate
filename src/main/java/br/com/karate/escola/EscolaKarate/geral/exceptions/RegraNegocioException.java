package br.com.karate.escola.EscolaKarate.geral.exceptions;

import java.io.Serial;

public class RegraNegocioException extends Exception {

    private static final long serialVersionUID = 1L;

    public RegraNegocioException(String message) {
        super(message);
    }

    public RegraNegocioException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegraNegocioException(Throwable cause) {
        super(cause);
    }
}
