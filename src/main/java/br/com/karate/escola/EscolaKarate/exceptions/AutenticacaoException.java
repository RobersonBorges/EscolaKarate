package br.com.karate.escola.EscolaKarate.exceptions;

public class AutenticacaoException extends RegraNegocioException {

    public AutenticacaoException(String mensagem) {
        super(mensagem);
    }

    public AutenticacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
