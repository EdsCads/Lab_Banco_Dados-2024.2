package ifma.modelo;

public enum TipoImovel {
    CASA("Casa"),
    APARTAMENTO("Apartamento"),
    KITNET("Kitnet"),
    SALA_COMERCIAL("Sala Comercial"),
    LOJA("Loja"),
    TERRENO("Terreno");

    private String descricao;

    TipoImovel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
} 