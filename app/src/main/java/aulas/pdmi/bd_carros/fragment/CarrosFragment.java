package aulas.pdmi.bd_carros.fragment;


import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import aulas.pdmi.bd_carros.R;
import aulas.pdmi.bd_carros.activity.CarroActivity;
import aulas.pdmi.bd_carros.activity.CarrosActivity;
import aulas.pdmi.bd_carros.adapter.CarrosAdapter;
import aulas.pdmi.bd_carros.model.Carro;
import aulas.pdmi.bd_carros.service.CarroServiceBD;

/**
 * Este fragmento controla a lista de carros.
 * Created by vagner on 18/08/16.
 */
public class CarrosFragment extends BaseFragment implements SearchView.OnQueryTextListener {

    protected RecyclerView recyclerView;
    private List<Carro> carros;
    private LinearLayoutManager linearLayoutManager;
    private String tipo; //o tipo de carro que é recebido como argumento na construção do fragmento
    private CarroServiceBD carroServiceBD; //instância de acesso ao bd

    public CarrosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //cria o bd, se ainda não estiver sido criado
        //ou obtém a instância de acesso ao bd
        carroServiceBD = CarroServiceBD.getInstance(getContext());

        //se há argumentos, o armazena para filtar a lista de carros
        if (getArguments() != null) {
            this.tipo = getArguments().getString("tipo");
        }

        setHasOptionsMenu(true); //habilita inflar menu no Fragmento

        ((CarrosActivity) getActivity()).getSupportActionBar().setTitle(R.string.titulo_fragmentcarros);  //um título para a janela

        //se for Android 6.+ requisita permissão para acessar o sistema de arquivos
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {

        }else{

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carros, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_fragmentcaroos); //mapeia a RecyclerView
        linearLayoutManager = new LinearLayoutManager(getActivity()); //constrói o gerenciador de layout
        recyclerView.setLayoutManager(linearLayoutManager); //adiciona o gerenciador
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //gerenciador de animação
        recyclerView.setHasFixedSize(true);

        //busca os dados na base de dados
        new Task().execute();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_carros, menu); //infla o menu

        //SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView(); //mapeia a SearchView
        searchView.setQueryHint(getString(R.string.hint_pesquisar)); //insere um hint
        searchView.setOnQueryTextListener(this); //adiciona o tratador a fila de ouvintes
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //toast("Ao pressionar enter.");
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Carro> carroList = new ArrayList<>(); //uma lista para nova camada de modelo da RecyclerView

        for(Carro carro : carros){ //um for-eatch na lista de carros
            if(carro.nome.contains(newText)) { //se o nome do carro contém o texto digitado
                carroList.add(carro); //adiciona o carro na nova lista
            }
        }

        //Context, fonte de dados, tratador do evento onClick
        recyclerView.setAdapter(new CarrosAdapter(getContext(), carroList, onClickCarro()));

        return true;
    }

    /*
        Este método utiliza a interface declarada na classe CarrosAdapter para tratar o evento onClick do item da lista.
     */
    protected CarrosAdapter.CarroOnClickListener onClickCarro() {
        //chama o contrutor da interface (implícito) para criar uma instância da interface declarada no adaptador.
        return new CarrosAdapter.CarroOnClickListener() {
            // Aqui trata o evento onItemClick.
            @Override
            public void onClickCarro(View view, int idx) {
                //armazena o carro que foi clicado
                Carro carro = carros.get(idx);
                //chama outra Activity para detalhar ou editar o carro clicado pelo usuário
                Intent intent = new Intent(getContext(), CarroActivity.class); //configura uma Intent explícita
                intent.putExtra("carro", carro); //inseri um extra com a referência para o objeto Carro
                intent.putExtra("qualFragmentAbrir", "CarroDetalheFragment");
                startActivity(intent);
            }
        };
    }

    /*
        Classe interna para operaçẽos assíncronas na base de dados.
     */
    private class Task extends AsyncTask<Void, Void, List<Carro>>{ //<Params, Progress, Result>

        List<Carro> carros;

        @Override
        protected List<Carro> doInBackground(Void... voids) {
            //busca os carros em background, em uma thread exclusiva para esta tarefa.
            if(CarrosFragment.this.tipo.equals(getString(R.string.tabs_classicos))){
                return carroServiceBD.getByTipo(getString(R.string.tabs_classicos));
            }else if(CarrosFragment.this.tipo.equals(getString(R.string.tabs_esportivos))) {
                return carroServiceBD.getByTipo(getString(R.string.tabs_esportivos));
            }if(CarrosFragment.this.tipo.equals(getString(R.string.tabs_luxo))){
                return carroServiceBD.getByTipo(getString(R.string.tabs_luxo));
            }else if(CarrosFragment.this.tipo.equals(getString(R.string.tabs_todos))) {
                return carroServiceBD.getAll();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Carro> carros) {
            super.onPostExecute(carros);
            //copia a lista de carros para uso no onQueryTextChange()
            CarrosFragment.this.carros = carros;
            //atualiza a view na UIThread
            recyclerView.setAdapter(new CarrosAdapter(getContext(), carros, onClickCarro())); //Context, fonte de dados, tratador do evento onClick
        }
    }//fim classe interna

}
