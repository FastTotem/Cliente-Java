public class ParametroAlerta {

    private Integer idParametroAlerta;
    private String componente;
    private Integer fkEmpresa;
    private Integer ideal;
    private Integer alerta;
    private Integer critico;
    private Integer notificacao;

    public ParametroAlerta() {
    }

    public Integer getIdParametroAlerta() {
        return idParametroAlerta;
    }

    public void setIdParametroAlerta(Integer idParametroAlerta) {
        this.idParametroAlerta = idParametroAlerta;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getIdeal() {
        return ideal;
    }

    public void setIdeal(Integer ideal) {
        this.ideal = ideal;
    }

    public Integer getAlerta() {
        return alerta;
    }

    public void setAlerta(Integer alerta) {
        this.alerta = alerta;
    }

    public Integer getCritico() {
        return critico;
    }

    public void setCritico(Integer critico) {
        this.critico = critico;
    }

    public Integer getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Integer notificacao) {
        this.notificacao = notificacao;
    }
}
