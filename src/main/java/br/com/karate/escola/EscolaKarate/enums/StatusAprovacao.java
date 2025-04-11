package br.com.karate.escola.EscolaKarate.enums;

public enum StatusAprovacao {
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    RECUPERACAO("Recuperação");
    private String descricao;
    StatusAprovacao(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
    public static StatusAprovacao fromDescricao(String descricao) {
        for (StatusAprovacao status : values()) {
            if (status.getDescricao().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de aprovação inválido: " + descricao);
    }
}
