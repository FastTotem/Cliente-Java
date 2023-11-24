public enum ParametroAlertaEnum {

    IDEAL("Ideal"),
    ALERTA("Alerta"),
    CRITICO("Critico"),
    NOTIFICACAO("Notificacao");

    private String status;

    ParametroAlertaEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
