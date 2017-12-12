package pt.isec.tiagodaniel.xadrez.Activities;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;
import pt.isec.tiagodaniel.xadrez.R;
import pt.isec.tiagodaniel.xadrez.States.EstadoEscolheDestino;
import pt.isec.tiagodaniel.xadrez.States.EstadoEscolhePeca;

public class JogarContraPCActivity extends Activity {
    LinearLayout ll;
    GameModel gameModel;
    ArrayList<Posicao> posicoesDisponiveisAnteriores = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar_contra_pc);

        ll = findViewById(R.id.tabuleiro);

        //tabuleiro=new Tabuleiro(ll);
        this.gameModel = new GameModel(this.ll);

        this.posicoesDisponiveisAnteriores = new ArrayList<>();
    }

    public void onClickQuadrado(View v) {
        ArrayList<Posicao> posicoesDisponiveis = new ArrayList<>();
        String res = getResources().getResourceEntryName(v.getId());
        Resources resources = getResources();
        ImageView iv;
        Peca pecaClick;

        int linha = Character.getNumericValue(res.charAt(1));
        char coluna = res.charAt(0);

        //if((pecaClick=tabuleiro.getPosicao(linha,coluna).getPeca())!=null)
        if (this.gameModel.getState() instanceof EstadoEscolhePeca) {
            pecaClick = this.gameModel.getTabuleiro().getPosicao(linha, coluna).getPeca();

            if (pecaClick == null)
                return;

            if (pecaClick.getJogador() == this.gameModel.getTabuleiro().getJogadorAtual()) {
                posicoesDisponiveis = pecaClick.getDisponiveis();
                this.gameModel.seguinte(this.gameModel.getTabuleiro().getPosicao(linha, coluna));
            }

        } else if (this.gameModel.getState() instanceof EstadoEscolheDestino) {
            pecaClick = this.gameModel.getTabuleiro().getPosicao(linha, coluna).getPeca();
            if (pecaClick.getJogador() == this.gameModel.getTabuleiro().getJogadorAtual()) {
                this.resetPosicoesDisponiveisAnteriores();
                posicoesDisponiveis = pecaClick.getDisponiveis();
            } else {
                this.gameModel.seguinte(this.gameModel.getTabuleiro().getPosicao(linha, coluna));
            }
        }

        ViewGroup.MarginLayoutParams mlp;
        for (Posicao p : posicoesDisponiveis) {
            iv = findViewById(resources.getIdentifier("" + p.getColuna() + p.getLinha(), "id", getBaseContext().getPackageName()));
            iv.setBackgroundColor(Color.BLACK);
            this.posicoesDisponiveisAnteriores.add(p);
        }

    }

    private void resetPosicoesDisponiveisAnteriores() {
        ImageView pecaImageView;
        for (Posicao posicao : this.posicoesDisponiveisAnteriores) {
            pecaImageView = findViewById(getResources().getIdentifier("" + posicao.getColuna()
                    + posicao.getLinha(), "id", getBaseContext().getPackageName()));

            if ((int) posicao.getColuna() % 2 != 0) {
                if (posicao.getLinha() % 2 != 0) {
                    pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabLight));
                } else {
                    pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabDark));
                }
            } else {
                if (posicao.getLinha() % 2 != 0) {
                    pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabDark));
                } else {
                    pecaImageView.setBackgroundColor(getResources().getColor(R.color.tabLight));
                }
            }
        }
    }
}
