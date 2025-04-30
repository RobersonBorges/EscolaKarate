package br.com.karate.escola.EscolaKarate.geral.enums;

public enum TipoAvaliacao {
    PARCIAL("Parcial"),
    BIMESTRAL("Bimestral");

    private String descricao;

    TipoAvaliacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAvaliacao fromDescricao(String descricao) {
        for (TipoAvaliacao tipo : values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de avaliação inválido: " + descricao);
    }
}
