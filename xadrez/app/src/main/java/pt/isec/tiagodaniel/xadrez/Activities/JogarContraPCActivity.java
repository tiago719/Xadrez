package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.security.AccessController;
import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;
import pt.isec.tiagodaniel.xadrez.Logic.Tabuleiro;
import pt.isec.tiagodaniel.xadrez.R;

public class JogarContraPCActivity extends Activity
{
    Tabuleiro tabuleiro;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_contra_pc);

        ll=findViewById(R.id.tabuleiro);

        tabuleiro=new Tabuleiro(ll);
    }

    public void onClickQuadrado(View v)
    {
        ArrayList<Posicao> posicoesDisponiveis=new ArrayList<>();
        String res= getResources().getResourceEntryName(v.getId());
        Resources resources=getResources();
        ImageView iv;
        Peca pecaClick;

        int linha=Character.getNumericValue(res.charAt(1));
        char coluna=res.charAt(0);

        if((pecaClick=tabuleiro.getPosicao(linha,coluna).getPeca())!=null)
            posicoesDisponiveis=pecaClick.getDisponiveis();

        ViewGroup.MarginLayoutParams mlp;
        for(Posicao p : posicoesDisponiveis)
        {
            iv=findViewById(resources.getIdentifier(""+p.getColuna()+p.getLinha(),"id",getBaseContext().getPackageName()));
            iv.setBackgroundColor(Color.BLACK);
        }

    }


}
