package aulas.pdmi.bd_carros.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Esta Activity contém os atributos e métodos que são comuns a todas
 * activities do projeto.
 * Created by vagner on 18/08/16.
 */
public class BaseActivity extends AppCompatActivity {
    protected static String TAG = "bdcarros";

    public void replaceFragment(int container, Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(container,fragment).commit();
    }

}
