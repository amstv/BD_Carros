package aulas.pdmi.bd_carros.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import aulas.pdmi.bd_carros.R;
import aulas.pdmi.bd_carros.activity.CarroActivity;
import aulas.pdmi.bd_carros.model.Carro;
import aulas.pdmi.bd_carros.service.CarroServiceBD;

/**
 * Este fragmento controla a edição do carro.
 * Created by vagner on 15/05/16.
 */
public class CarroEdicaoFragment extends BaseFragment {

    private Carro carro; //uma instância da classe Carro com escopo global para utilização em membros da classe
    private final int SAVE = 0;
    private final int DELETE = 1;
    //componentes <-> objeto carro
    private ImageView imageViewFoto; //campo referente ao atributo url_foto
    private RadioButton rbClassicos, rbEsportivos, rbLuxo; //campos referente ao tipo do objeto carro
    private EditText editTextNome; //campo referente ao atributo nome do objeto carro
    private EditText editTextDescricao; //campo referente ao atributo descrição do objeto carro
    private EditText editTextLatitude;  //campo referente ao atributo latitude do objeto carro
    private EditText editTextLongitude; //campo referente ao atributo longitude do objeto carro
    private EditText editTextUrlVideo; //campo referente ao atributo url_video do objeto carro


    //utilizado pelo Fragment para repassar o objeto carro clicado na lista pelo user
    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //informa ao sistema que o fragment irá adicionar menu na ActionBar
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //infla o layout
        View view = inflater.inflate(R.layout.fragment_edicaocarro, container, false);

        //um título para a janela
        ((CarroActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_edicaocarro);

        Log.d(TAG, "Dados do registro = " + carro); //um log para depurar

        //carrega a imagem
        Log.d(TAG, "URL foto = " + carro.urlFoto); //um log para depurar
        imageViewFoto = (ImageView) view.findViewById(R.id.imv_card0_fredicaocarro);
        if(carro.urlFoto != null){
            imageViewFoto.setImageURI(Uri.parse(carro.urlFoto));
        }
        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cria uma Intent
                //primeiro argumento: ação ACTION_PICK "escolha um item a partir dos dados e retorne o seu URI"
                //segundo argumento: refina a ação para arquivos de imagem, retornando um URI
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //inicializa uma Activity. Neste caso, uma que forneca acesso a galeria de imagens do dispositivo.
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });

        //carrega o tipo nos RadioButtons
        Log.d(TAG, "Tipo = " + carro.tipo); //um log para depurar
        rbClassicos = (RadioButton) view.findViewById(R.id.rbclassicos_card1_fredicaocarro);
        rbEsportivos = (RadioButton) view.findViewById(R.id.rbesportivos_card1_fredicaocarro);
        rbLuxo = (RadioButton) view.findViewById(R.id.rbluxo_card1_fredicaocarro);
        if (carro.tipo.equals(getContext().getResources().getString(R.string.tipo_classicos))){
            rbClassicos.setChecked(true);
        } else if (carro.tipo.equals(getContext().getResources().getString(R.string.tipo_esportivos))) {
            rbEsportivos.setChecked(true);
        } else {
            rbLuxo.setChecked(true);
        }

        //carrega o nome e a descrição
        Log.d(TAG, "Nome = " + carro.nome + "\nDescrição = " + carro.desc); //um log para depurar
        editTextNome = (EditText) view.findViewById(R.id.etNome_card2_fredicaocarro);
        editTextDescricao = (EditText) view.findViewById(R.id.etDescricao_card2_fredicaocarro);
        editTextNome.setText(carro.nome);
        editTextDescricao.setText(carro.desc);

        //carrega a latitude e a longitude
        Log.d(TAG, "Latitude = " + carro.latitude + "\nlongitude = " + carro.longitude); //um log para depurar
        editTextLatitude = (EditText) view.findViewById(R.id.etlatitude_card3_fredicaocarro);
        editTextLongitude = (EditText) view.findViewById(R.id.etlongitude_card3_fredicaocarro);
        editTextLatitude.setText(carro.latitude);
        editTextLongitude.setText(carro.longitude);

        //carrega o vídeo
        Log.d(TAG, "URL vídeo = " + carro.urlVideo); //um log para depurar
        editTextUrlVideo = (EditText) view.findViewById(R.id.etURLVideo__card4_fredicaocarro);
        if(carro.urlVideo != null){
            editTextUrlVideo.setText(Uri.parse(carro.urlVideo).toString());
        }
        editTextUrlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cria uma Intent
                //primeiro argumento: ação ACTION_PICK "escolha um item a partir dos dados e retorne o seu URI"
                //segundo argumento: refina a ação para arquivos de vídeo, retornando um URI
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                //inicializa uma Activity. Neste caso, uma que forneca acesso a galeria de imagens do dispositivo.
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });


        return view;
    }

    /*
        Infla o menu.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_edicaocarro, menu);
    }

    /*
        Trata eventos dos itens de menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:{
                //carrega os dados do formulário no objeto
                //uri da foto e do vídeo são carregados no método onActivityResult()
                carro.nome = editTextNome.getText().toString();
                carro.desc = editTextDescricao.getText().toString();
                carro.latitude = editTextLatitude.getText().toString();
                carro.longitude = editTextLongitude.getText().toString();
                if(rbClassicos.isChecked()){
                    carro.tipo = getContext().getResources().getString(R.string.tipo_classicos);
                }else if(rbEsportivos.isChecked()){
                    carro.tipo = getContext().getResources().getString(R.string.tipo_esportivos);
                }else {
                    carro.tipo = getContext().getResources().getString(R.string.tipo_luxo);
                }
                new CarrosTask().execute(SAVE); //executa a operação CREATE em uma thread AsyncTask
                break;
            }
            case R.id.menuitem_excluir:{
                new CarrosTask().execute(DELETE); //executa a operação DELETE em uma thread AsyncTask
                break;
            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return false;
    }

    /**
     * Método que recebe o retorno da Activity de galeria de imagens.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            Log.d(TAG, data.toString());
            Uri arquivoUri = data.getData(); //obtém o URI
            Log.d(TAG, "URI do arquivo: " + arquivoUri);
            if(arquivoUri.toString().contains("images")) {
                imageViewFoto.setImageURI(arquivoUri); //coloca a imagem no ImageView
                carro.urlFoto = arquivoUri.toString(); //armazena o Uri da imagem no objeto do modelo
            }else if(arquivoUri.toString().contains("video")) {
                editTextUrlVideo.setText(arquivoUri.toString()); //coloca a URL do vídeo no EditText
                carro.urlVideo = arquivoUri.toString(); //armazena o Uri do vídeo no objeto do modelo
            }
        }
    }


    /*
        Classe interna que extende uma AsyncTask.
        Lembrando: A AsyncTask gerencia a thread que acessa os dados no banco de dados.
    */
    private class CarrosTask extends AsyncTask<Integer, Void, Long>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertWait(R.string.alert_title_wait, R.string.alert_message_processando);
        }

        @Override
        protected Long doInBackground(Integer... params) {
            //executa a tarefa em background, em uma thread exclusiva para esta tarefa.
            switch(params[0]){
                case SAVE:
                    return CarroServiceBD.getInstance(getContext()).save(carro); //salva o carro no BD
                case DELETE:
                    return CarroServiceBD.getInstance(getContext()).delete(carro); //deleta o carro do BD
                default: return 0L;
            }

        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            alertWaitDismiss();
            if(aLong > 0){
                //faz aparecer uma caixa de diálogo confirmando a operação
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_realizadacomsucesso);
            }else{
                //faz aparecer uma caixa de diálogo confirmando problemas na operação
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_erroaorealizaroperacao);
            }
        }
    }//fim classe interna

}//fim classe externa
