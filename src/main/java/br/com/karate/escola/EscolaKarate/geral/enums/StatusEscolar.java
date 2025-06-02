package br.com.karate.escola.EscolaKarate.geral.enums;

public enum StatusEscolar {
    ATIVO("Aprovado"),
    CONCLUIDO("Reprovado"),
    TRANSFERIDO("Recuperação"),
    DESISTENTE("Desistente");

    private String descricao;

    StatusEscolar(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusEscolar fromDescricao(String descricao) {
        for (StatusEscolar status : values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status escolar inválido: " + descricao);
    }
}
