package aulas.pdmi.bd_carros.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import aulas.pdmi.bd_carros.R;
import aulas.pdmi.bd_carros.fragment.CarrosFragment;

/**
 * Adaptador para o ViewPager.
 * Created by vagner on 28/05/16.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    protected static final String TAG = "TabsAdapter";
    private Context context;

    public TabsAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    /*
        Constrói cada ViewPager. Nesta construção associa um controlador para a View.
        ATENÇÃO: Este método é chamado baseado no valor definido no getCount().
     */
    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        Fragment f = null;

        switch (position){
            case 0:
                f = new CarrosFragment(); //controlador para a aba de índice um
                args.putString("tipo", context.getString(R.string.tabs_classicos));
                break;
            case 1:
                f = new CarrosFragment(); //controlador para a aba de índice dois
                args.putString("tipo", context.getString(R.string.tabs_esportivos));
                break;
            case 2:
                f = new CarrosFragment(); //controlador para a aba de índice três
                args.putString("tipo", context.getString(R.string.tabs_luxo));
                break;
            case 3:
                f = new CarrosFragment(); //controlador para a aba de índice zero
                args.putString("tipo", context.getString(R.string.tabs_todos));
                break;
        }

        f.setArguments(args);

        return f;
    }

    /*
        Define a quantidade de objetos ViewPager. Na visão do usuário, define a quantidade de abas que aparecerão na Toolbar.
     */
    @Override
    public int getCount() {
        return 4;
    }


    /*
        Insere os títulos nas abas da Toolbar.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.tabs_classicos);
            case 1:
                return context.getString(R.string.tabs_esportivos);
            case 2:
                return context.getString(R.string.tabs_luxo);
            case 3:
                return context.getString(R.string.tabs_todos);
        }
        return null;
    }
}