package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.XadrezApplication;
import pt.isec.tiagodaniel.xadrez.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onJogarContraPC(View v) {
        Intent intent = new Intent(this, JogarContraPCActivity.class);
        intent.setAction(Constantes.ACTION_JOGvsPC);
        startActivity(intent);
    }

    public void onModo2Jogadores(View v) {
        Intent intent = new Intent(this, Configurar2Jogadores.class);
        intent.setAction(Constantes.ACTION_JOGvsJOG);
        startActivity(intent);
    }

    public void onCriarJogoOnline(View v) {

    }

    public void onJuntarJogoOnline(View v) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuPrincipal) {

            Intent intent = new Intent(this, PerfilActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
