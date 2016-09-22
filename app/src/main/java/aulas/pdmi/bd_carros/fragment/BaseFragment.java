package aulas.pdmi.bd_carros.fragment;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import aulas.pdmi.bd_carros.R;

/**
 * Este fragmento contém os atributos e métodos que são comuns a todas
 * fragmentos do projeto.
 * Created by vagner on 18/08/16.
 */
public class BaseFragment extends Fragment{
    protected static String TAG = "bdcarros";
    private AlertDialog dialog;

    /*
        Toasts personalizados.
     */
    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*
        Alertas personalizados.
     */
    protected void alertOk(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //cria um buider
        builder.setTitle(title).setMessage(message); //insere o título e a mensagem
        // Adiciona o botão e trata o evento onClick
        builder.setPositiveButton(R.string.alertdialog_buttom_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //volta para a lista de carros
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create(); //cria o alerta
        dialog.show(); //apresenta a caixa de diálogo
    }

    protected void alertOk(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //cria um buider
        builder.setTitle(title).setMessage(message); //insere o título e a mensagem
        // Adiciona o botão e trata o evento onClick
        builder.setPositiveButton(R.string.alertdialog_buttom_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //volta para a lista de carros
                getActivity().finish();
            }
        });
        AlertDialog dialog = builder.create(); //cria o alerta
        dialog.show(); //apresenta a caixa de diálogo
    }

    protected void alertWait(int title, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //cria um buider
        builder.setTitle(title).setMessage(message); //insere o título e a mensagem
        dialog = builder.create(); //cria o alerta
        dialog.show(); //apresenta a caixa de diálogo
    }

    protected void alertWaitDismiss(){
        if(dialog != null){
            dialog.dismiss();
        }
    }
}
