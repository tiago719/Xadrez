package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.LinearLayout;

import static pt.isec.tiagodaniel.xadrez.Logic.Constantes.TABULEIRO_COLUNAS;

/**
 * Created by Tiago Coutinho on 05/12/2017.
 */

public class Jogador1 extends Jogador
{
    public Jogador1(Tabuleiro t, LinearLayout ll)
    {
        super(t);

        pecasTabuleiro.add(new Torre(t, t.getPosicao(1,1),this, (LinearLayout) ll.getChildAt(0)));
        pecasTabuleiro.add(new Cavalo(t, t.getPosicao(1, 2),this, (LinearLayout) ll.getChildAt(0)));
        pecasTabuleiro.add(new Bispo(t, t.getPosicao(1, 3),this, (LinearLayout) ll.getChildAt(0)));
        pecasTabuleiro.add(new Rainha(t, t.getPosicao(1, 4),this, (LinearLayout) ll.getChildAt(0)));
        pecasTabuleiro.add(new Rei(t, t.getPosicao(1, 5),this, (LinearLayout) ll.getChildAt(0)));
        pecasTabuleiro.add(new Bispo(t, t.getPosicao(1, 6),this, (LinearLayout) ll.getChildAt(0)));
        pecasTabuleiro.add(new Cavalo(t, t.getPosicao(1, 7),this, (LinearLayout) ll.getChildAt(0)));
        pecasTabuleiro.add(new Torre(t, t.getPosicao(1, 8),this, (LinearLayout) ll.getChildAt(0)));

        for(int i=1;i<=TABULEIRO_COLUNAS;i++)
        {
            pecasTabuleiro.add(new Peao(t,t.getPosicao(2, i),this,(LinearLayout) ll.getChildAt(1)));
        }
    }
}
