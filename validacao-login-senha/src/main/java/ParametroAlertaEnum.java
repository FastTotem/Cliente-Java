public enum ParametroAlertaEnum {

    IDEAL("ideal"),
    ALERTA("alerta"),
    CRITICO("critico"),
    NOTIFICACAO("Notificacao");

    private String status;

    ParametroAlertaEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
