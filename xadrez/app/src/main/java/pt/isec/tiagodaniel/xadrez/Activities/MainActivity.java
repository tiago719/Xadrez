package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onJogarContraPC(View v) {
        Intent intent = new Intent(this, JogarContraPCActivity.class);
        startActivity(intent);
    }

    public void onModo2Jogadores(View v) {
        Intent intent = new Intent(this, Modo2JogadoresActivity.class);
        startActivity(intent);
    }

    public void onCriarJogoOnline(View v) {
        Intent intent = new Intent(this, CriarJogoOnlineActivity.class);
        startActivity(intent);
    }

    public void onJuntarJogoOnline(View v) {
        Intent intent = new Intent(this, JuntarJogoOnlineActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuPerfil) {

            Intent intent = new Intent(this, PerfilActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
