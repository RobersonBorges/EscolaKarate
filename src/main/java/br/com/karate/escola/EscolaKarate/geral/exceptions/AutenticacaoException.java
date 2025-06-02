package br.com.karate.escola.EscolaKarate.geral.exceptions;

public class AutenticacaoException extends RegraNegocioException {

    public AutenticacaoException(String mensagem) {
        super(mensagem);
    }

    public AutenticacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
