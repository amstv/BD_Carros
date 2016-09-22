package aulas.pdmi.bd_carros.model;

/**
 * Classe do domínio da app.
 * Representa o tipo de objeto que a app irá manipular no controlador.
 * Created by vagner on 18/08/16.
 */

import android.net.Uri;
import java.io.Serializable;

public class Carro implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long id;
    public String nome;
    public String desc;
    public String tipo;
    public String urlFoto;
    public String urlVideo;
    public String latitude;
    public String longitude;

    @Override
    public String toString() {
        return "Carro{"
                + "id='" + id + '\''
                + ", tipo='" + tipo + '\''
                + ", nome='" + nome + '\''
                + ", desc='" + desc + '\''
                + ", urlFoto='" + urlFoto + '\''
                + ", urlVideo='" + urlVideo + '\''
                + ", latitude='" + latitude + '\''
                + ", longitude='" + longitude + '\''
                + '}';
    }
}
