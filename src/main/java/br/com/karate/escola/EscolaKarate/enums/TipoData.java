package br.com.karate.escola.EscolaKarate.enums;

public enum TipoData {
    FERIADO("Feriado"), RECESSO("Recesso"), INICIO_PERIODO("Início de período"), FIM_PERIODO("Fim de período"), EVENTO("Evento"), PROVA("Prova"), OUTRO("Outros");
    private final String descricao;

    TipoData(String descricao) {
        this.descricao = descricao;
    }


    public static TipoData fromString(String tipo) {
        for (TipoData data : TipoData.values()) {
            if (data.name().equalsIgnoreCase(tipo)) {
                return data;
            }
        }
        throw new IllegalArgumentException("Tipo de data inválido: " + tipo);
    }
}
