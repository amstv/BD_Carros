package aulas.pdmi.bd_carros.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import aulas.pdmi.bd_carros.R;
import aulas.pdmi.bd_carros.adapter.TabsAdapter;

/**
 * Esta Activity inicializa a app.
 * O seu conteúdo é controlado pelo fragmento CarrosFragment.
 * Created by vagner on 18/08/16.
 */
public class CarrosActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        TabLayout.OnTabSelectedListener{

    //paginação de view para responder ao swipe
    private ViewPager viewPager;


    /*
        Métodos do ciclo de vida da Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //associa o layout a Activity
        setContentView(R.layout.activity_carros);

        //ToolBar
        //mapeia a ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //insere a ToolBar como ActionBar (o estilo deve estar como .NoActionBar, veja o arquivo values/styles).
        setSupportActionBar(toolbar);

        //FloatButton
        //mapeia o FloatButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //Trata eventos do FloatButton
            @Override
            public void onClick(View view) {
                //configura uma Intent explícita
                Intent intent = new Intent(CarrosActivity.this, CarroActivity.class);
                //indica para a outra Activity qual o fragmento deve abrir
                intent.putExtra("qualFragmentAbrir", "NovoCarroFragment");
                //chama outra Activity
                startActivity(intent);
            }
        });

        //Drawer
        //mapeia o container da NavigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //cria e configura o botão da Drawer na ActionBar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle); //insere o handler na fila de ouvintes
        toggle.syncState(); //sincroniza o estado

        //NavigationView
        //mapeia a NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //insere o handler na fila de ouvintes do componente
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // ViewPager
        //mapeia a ViewPager
        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        //cria um adaptador para a ViewPager
        TabsAdapter adapter = new TabsAdapter(this, getSupportFragmentManager());
        //associa o adaptador a ViewPager
        viewPager.setAdapter(adapter);
        //define a quantidades de páginas
        viewPager.setOffscreenPageLimit(4);

        // Tabs
        //mapeia o container das tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //associa a ViewPager ao container
        tabLayout.setupWithViewPager(viewPager);
        //define que é rolável
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //insere o handler na lista de ouvintes do componente
        tabLayout.setOnTabSelectedListener(this);
    }

    //chamado ao pressionar o botão voltar na barra de navegação do device.
    @Override
    public void onBackPressed() {
        //mapeia o container da NavigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //faz esconder a Drawer se ela estiver aparente
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { //senão, desempilha a Activity da pilha de navegação
            super.onBackPressed();
        }
    }


    /*
        Trata eventos do menu da NavigationView.
        Deve ser utilizado para a Navegação da app.
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_fragment_list_carros:
                //não faz nada por enquanto
                break;
            case R.id.nav_fragment_sobre:
                Toast.makeText(CarrosActivity.this, "Em desenvolvimento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(CarrosActivity.this, "Em desenvolvimento", Toast.LENGTH_SHORT).show();
                break;
        }

        //mapeia o container da NavigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //fecha a drawer
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /*
        Tratam eventos das tabs.
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //seta a página atual da ViewPager
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
