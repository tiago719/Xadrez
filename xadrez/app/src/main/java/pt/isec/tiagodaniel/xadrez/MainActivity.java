package pt.isec.tiagodaniel.xadrez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onJogarContraPC(View v)
    {
        Intent intent=new Intent(this,JogarContraPCActivity.class);
        startActivity(intent);
    }

    public void onModo2Jogadores(View v)
    {
        Intent intent=new Intent(this,Modo2JogadoresActivity.class);
        startActivity(intent);
    }

    public void onCriarJogoOnline(View v)
    {
        Intent intent=new Intent(this,CriarJogoOnlineActivity.class);
        startActivity(intent);
    }

    public void onJuntarJogoOnline(View v)
    {
        Intent intent=new Intent(this,JuntarJogoOnlineActivity.class);
        startActivity(intent);
    }
}
