package br.com.karate.escola.EscolaKarate.exceptions;

public class UsuarioExistenteException extends RegraNegocioException{

    public UsuarioExistenteException(String mensagem) {
        super(mensagem);
    }
}
