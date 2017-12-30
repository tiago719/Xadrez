package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;

import pt.isec.tiagodaniel.xadrez.R;

public class ActivityPromocaoPeao extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocao_peaoa);

        /*DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int) (height*0.6));*/
    }
    public void onRainha(View v) {
        setResult(Constantes.RAINHA_ESCOLHIDA);
        finish();
    }
    public void onTorre(View v) {
        setResult(Constantes.TORRE_ESCOLHIDA);
        finish();
    }
    public void onBispo(View v) {
        setResult(Constantes.BISPO_ESCOLHIDA);
        finish();
    }
    public void onCavalo(View v) {
        setResult(Constantes.CAVALO_ESCOLHIDA);
        finish();
    }

    @Override
    public void onBackPressed()
    {

    }
}
