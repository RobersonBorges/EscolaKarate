package br.com.karate.escola.EscolaKarate.geral.exceptions;

public class UsuarioExistenteException extends RegraNegocioException{

    public UsuarioExistenteException(String mensagem) {
        super(mensagem);
    }
}
