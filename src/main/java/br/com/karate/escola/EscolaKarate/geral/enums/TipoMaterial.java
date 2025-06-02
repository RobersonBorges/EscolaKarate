package br.com.karate.escola.EscolaKarate.geral.enums;

public enum TipoMaterial {
    APOSTILA("Apostila"),
    LIVRO("Livro"),
    VIDEO("Vídeo"),
    EXERCICIO("Exercício"),
    PROVA_ANTIGA("Prova Antiga"),
    OUTRO("Outro");

    private final String descricao;

    TipoMaterial(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoMaterial fromDescricao(String descricao) {
        for (TipoMaterial tipo : values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de material inválido: " + descricao);
    }
}