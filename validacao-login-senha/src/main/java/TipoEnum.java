public enum TipoEnum {

    MEMORIA("memoria"),
    PROCESSADOR("processador"),
    USB("usb"),
    DISCO("disco"),
    SISTEMA("sistema"),
    TEMPO_ATIVIDADE("tempoAtividade"),
    ESCRITA("escrita"),
    LEITURA("leitura");

    private String tipoCaptura;
    TipoEnum(String tipo) {
        tipoCaptura = tipo;
    }

    public String getTipoCaptura() {
        return tipoCaptura;
    }

}
