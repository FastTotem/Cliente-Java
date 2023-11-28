public enum TipoEnum {

    MEMORIA("memoria"),
    PROCESSADOR("processador"),
    USB("usb"),
    DISCO("disco"),
    SISTEMA("sistema"),
    TEMPO_ATIVIDADE("tempoAtividade"),
    ESCRITA("escrita"),
    LEITURA("leitura"),
    TAXA_TRANSFERENCIA("taxaTransferencia"),
    ARMAZENAMENTO("porcentagemArmazenamento");

    private final String tipoCaptura;

    TipoEnum(String tipo) {
        this.tipoCaptura = tipo;
    }

    public String getTipoCaptura() {
        return tipoCaptura;
    }
}
