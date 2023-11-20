public enum ParametroAlertaEnum {

    ALERTA("alerta"),
    CRITICO("critico"),
    IDEAL("ideal");

    private String status;

    ParametroAlertaEnum(String status) {
        this.status = status;
    }

}
