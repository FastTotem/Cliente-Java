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
        tipoComponente = String.valueOf(TipoEnum.DISCO);
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
            nomeComponente = discoT.getNome();
            idDiscos.add(inserirComponente());
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
            inserirCapturaComponente(discoT.getEscritas(), String.valueOf(TipoEnum.ESCRITA));
            inserirCapturaComponente(discoT.getLeituras(), String.valueOf(TipoEnum.LEITURA));
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
            inserirCapturaComponente(discoT.calcularReadWrite(), String.valueOf(TipoEnum.TAXA_TRANSFERENCIA));
        }

    }

//    public void inserirPorcentagemArmazenada(){
//        if (idDiscos.isEmpty()){
//            setIdDiscos();
//        }
//
//        if (discosT.get(0).getIdDisco() == null){
//            Integer idDisco;
//            for (int i = 0; i < idDiscos.size(); i++) {
//                idDisco = idDiscos.get(i);
//                discosT.get(i).setIdDisco(idDisco);
//            }
//        }
//        for(DiscoT discoT: discosT){
//            inserirCapturaComponente(discoT.calcularPorcentagemArmazenada(), String.valueOf(TipoEnum.ESCRITA), discoT.getIdDisco());
//        }
//    }

    public void setIdDiscos(){
        idDiscos = getListaIdComponente();
    }


    public List<DiscoT> getDiscosT() {
        return discosT;
    }

}
