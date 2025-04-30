package br.com.karate.escola.EscolaKarate.geral.enums;

public enum TipoEvento {
    CULTURAL("Cultural"),
    ACADEMICO("Acadêmico"),
    ESPORTIVO("Esportivo"),
    COMEMORATIVO("Comemorativo"),
    OUTRO("Outro");

    private final String descricao;

    TipoEvento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoEvento fromDescricao(String descricao) {
        for (TipoEvento tipo : values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de evento inválido: " + descricao);
    }
}