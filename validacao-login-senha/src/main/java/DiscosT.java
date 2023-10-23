import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;

import java.util.ArrayList;
import java.util.List;

public class DiscosT extends Componente {
    private DiscoGrupo discogrupo;
    private List<Disco> discos;
    private List<DiscoT> discosT;
    private List<Integer> idDiscos;

    public DiscosT() {
        discogrupo = new DiscoGrupo();
        discos = discogrupo.getDiscos();

        discosT = new ArrayList<DiscoT>();
        idDiscos = new ArrayList<Integer>();

        for (Disco disco: discos) {
            DiscoT novoDisco = new DiscoT(disco);
            discosT.add(novoDisco);
        }
    }

    public void inserirDiscos(){

        for (DiscoT discoT : discosT){
            idDiscos.add(inserirComponente(String.valueOf(TipoEnum.DISCO), discoT.getNome()));
        }

    }

    public void inserirCapturasDisco(){

        if (idDiscos.isEmpty()){
            setIdDiscos();
        }

        if (discosT.get(0).getIdDisco() == null){
            Integer idDisco;
            for (int i = 0; i < idDiscos.size(); i++) {
                idDisco = idDiscos.get(i);
                discosT.get(i).setIdDisco(idDisco);
            }
        }

        for(DiscoT discoT: discosT){
            inserirCapturaComponente(discoT.getEscritas(), String.valueOf(TipoEnum.ESCRITA), discoT.getIdDisco());
            inserirCapturaComponente(discoT.getLeituras(), String.valueOf(TipoEnum.LEITURA), discoT.getIdDisco());
        }

    }

    public void inserirReadWrite(){

        if (idDiscos.isEmpty()){
            setIdDiscos();
        }

        if (discosT.get(0).getIdDisco() == null){
            Integer idDisco;
            for (int i = 0; i < idDiscos.size(); i++) {
                idDisco = idDiscos.get(i);
                discosT.get(i).setIdDisco(idDisco);
            }
        }

        for(DiscoT discoT: discosT){
            inserirCapturaComponente(discoT.calcularReadWrite(), String.valueOf(TipoEnum.TAXA_TRANSFERENCIA), discoT.getIdDisco());
        }

    }

    public void setIdDiscos(){
        idDiscos = getListaIdComponente(String.valueOf(TipoEnum.DISCO));
    }


    public List<DiscoT> getDiscosT() {
        return discosT;
    }

}
