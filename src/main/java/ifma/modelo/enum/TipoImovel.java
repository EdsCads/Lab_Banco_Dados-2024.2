pacakge ifma.modelo.enum;

public enum TipoImovel {

    APARTAMENTO("Tipo Apartamento"),
    CASA("Tipo Casa");


    private final String tipo;

    private TipoImovel(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
