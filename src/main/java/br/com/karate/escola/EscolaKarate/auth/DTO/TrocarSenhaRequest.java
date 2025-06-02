package br.com.karate.escola.EscolaKarate.auth.DTO;

import lombok.Data;

@Data
public class TrocarSenhaRequest {

    private String userName;
    private String senhaAtual;
    private String novaSenha;
}
