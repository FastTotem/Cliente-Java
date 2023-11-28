public class
ParametroAlerta {

    private Integer idParametroAlerta;
    private String componente;
    private Integer fkEmpresa;
    private Double ideal;
    private Double alerta;
    private Double critico;
    private Double notificacao;

    public ParametroAlerta() {
    }

    public ParametroAlerta(Integer idParametroAlerta, String componente, Integer fkEmpresa, Double ideal, Double alerta, Double critico, Double notificacao) {
        this.idParametroAlerta = idParametroAlerta;
        this.componente = componente;
        this.fkEmpresa = fkEmpresa;
        this.ideal = ideal;
        this.alerta = alerta;
        this.critico = critico;
        this.notificacao = notificacao;
    }

    public Double getIdeal() {
        return ideal;
    }

    public void setIdeal(Double ideal) {
        this.ideal = ideal;
    }

    public Double getAlerta() {
        return alerta;
    }

    public void setAlerta(Double alerta) {
        this.alerta = alerta;
    }

    public Double getCritico() {
        return critico;
    }

    public void setCritico(Double critico) {
        this.critico = critico;
    }

    public Double getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Double notificacao) {
        this.notificacao = notificacao;
    }
}