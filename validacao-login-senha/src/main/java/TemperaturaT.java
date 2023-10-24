import com.github.britooo.looca.api.group.temperatura.Temperatura;

public class TemperaturaT extends Componente {
    private Temperatura temperatura;
    private Double valorTemperatura;

    public TemperaturaT() {
        this.temperatura = new Temperatura();
    }

    public Double getValorTemperatura() {
        return temperatura.getTemperatura();
    }

    public void inserirCapturaTemperatura() {

        valorTemperatura = temperatura.getTemperatura();

        Integer idProcessador = this.getIdComponente("PROCESSADOR", getFkTotem());

        this.inserirCapturaComponente(valorTemperatura, "TEMPERATURA", idProcessador);

    }
}