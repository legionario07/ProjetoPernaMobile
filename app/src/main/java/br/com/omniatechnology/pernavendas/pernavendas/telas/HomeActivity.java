package br.com.omniatechnology.pernavendas.pernavendas.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import br.com.omniatechnology.pernavendas.pernavendas.R;
import br.com.omniatechnology.pernavendas.pernavendas.utils.SessionUtil;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button imgVendas;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgVendas = findViewById(R.id.img_vendas);
        imgVendas.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fabNewVenda = findViewById(R.id.fabNovaVenda);
        fabNewVenda.setOnClickListener(this);

        menu = navigationView.getMenu();

        inativarMenu();

    }

    private void inativarMenu() {

        if (SessionUtil.getInstance().getUsuario().getPerfil().getId() != 1) {
            menu.getItem(0).setVisible(false); //Vendas
            menu.getItem(2).getSubMenu().getItem(5).setVisible(false); //Usuarios
        }

        menu.getItem(2).getSubMenu().getItem(6).setVisible(false); //Perfil
        menu.getItem(2).getSubMenu().getItem(7).setVisible(false); //Configurações

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categorias) {

            startActivity(new Intent(this, CategoriasActivity.class));

        } else if (id == R.id.nav_configuracao) {

            startActivity(new Intent(this, ConfiguracoesActivity.class));

        } else if (id == R.id.nav_combos) {

            startActivity(new Intent(this, CombosActivity.class));

        } else if (id == R.id.nav_marcas) {

            startActivity(new Intent(this, MarcasActivity.class));

        } else if (id == R.id.nav_perfil) {

            startActivity(new Intent(this, PerfilActivity.class));

        } else if (id == R.id.nav_produtos) {
            startActivity(new Intent(this, ProdutosActivity.class));

        } else if (id == R.id.nav_unidades_de_medidas) {

            startActivity(new Intent(this, UnidadesDeMedidasActivity.class));

        } else if (id == R.id.nav_usuarios) {

            startActivity(new Intent(this, UsuariosActivity.class));

        } else if (id == R.id.nav_vendas) {

            startActivity(new Intent(this, VendasActivity.class));

        } else if (id == R.id.nav_pedidos) {

            startActivity(new Intent(this, PedidosActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_vendas:


                startActivity(new Intent(this, PedidosActivity.class));

                break;

            case R.id.fabNovaVenda:


                startActivity(new Intent(this, NewVendaActivity.class));

                break;

            default:
        }

    }
}
