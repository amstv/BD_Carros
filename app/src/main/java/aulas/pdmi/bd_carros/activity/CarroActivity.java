package aulas.pdmi.bd_carros.activity;

import android.os.Bundle;
import android.util.Log;

import aulas.pdmi.bd_carros.R;
import aulas.pdmi.bd_carros.fragment.CarroDetalheFragment;
import aulas.pdmi.bd_carros.fragment.CarroNovoFragment;
import aulas.pdmi.bd_carros.model.Carro;

/**
 * Esta Activity é chamada para inserir um novo carro, detalhar ou editar um carro.
 * O seu conteúdo é controlado dinamicamente pelos fragmentos: CarroNovoFragment,
 * CarroDetalheFragment, e CarroEdicaoFragment.
 * Created by vagner on 29/08/16.
 */
public class CarroActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //associa um layout a esta Activity
        setContentView(R.layout.activity_carro);

        //obtém do extras da intent recebida o fragmento que deve abrir
        String msg = (String) getIntent().getCharSequenceExtra("qualFragmentAbrir");
        if(msg.equals("CarroNovoFragment")){
            replaceFragment(R.id.fragment_container, new CarroNovoFragment());
        }else if(msg.equals("CarroDetalheFragment")){
            //constrói uma instância do Fragment CarroDetalheFragment
            CarroDetalheFragment carroDetalheFragment = new CarroDetalheFragment();
            //insere o fragmento como conteúdo de content_main.xml
            replaceFragment(R.id.fragment_container, carroDetalheFragment);
            //obtém o carro que foi repassado pela CarrosActivity ao chamar esta Activity
            Carro carro = (Carro) getIntent().getSerializableExtra("carro");
            Log.d(TAG, "Objeto carro recebido: " + carro.toString()); //um log para o LogCat
            //repassa o objeto carro para o fragmento
            carroDetalheFragment.setCarro(carro);
        }
    }
}
