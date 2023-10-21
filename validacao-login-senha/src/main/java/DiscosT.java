import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;

import java.util.ArrayList;
import java.util.List;

public class DiscosT {
    private DiscoGrupo discogrupo;
    private List<Disco> discos;
    private List<DiscoT> discosT;

    public DiscosT() {
        discogrupo = new DiscoGrupo();
        discos = discogrupo.getDiscos();

        discosT = new ArrayList<DiscoT>();

        for (Disco disco: discos) {
            DiscoT novoDisco = new DiscoT(disco.getTamanho(), disco.getEscritas(), disco.getBytesDeEscritas(), disco.getNome(), disco.getModelo());
            discosT.add(novoDisco);
        }
    }

    public List<DiscoT> getDiscosT() {
        return discosT;
    }

}
