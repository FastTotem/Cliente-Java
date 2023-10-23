public enum TipoCapturaEnum {

    MEMORIA("memoria"), PROCESSADOR("processador"), USB("usb"), DISCO("disco"), SISTEMA("sistema");

    private String tipoCaptura;
    TipoCapturaEnum(String tipo) {
        tipoCaptura = tipo;
    }

    public String getTipoCaptura() {
        return tipoCaptura;
    }

}
