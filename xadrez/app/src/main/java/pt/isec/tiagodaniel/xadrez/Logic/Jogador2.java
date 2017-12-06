package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.LinearLayout;

import static pt.isec.tiagodaniel.xadrez.Logic.Constantes.TABULEIRO_COLUNAS;

/**
 * Created by Tiago Coutinho on 05/12/2017.
 */

public class Jogador2 extends Jogador
{
    public Jogador2(Tabuleiro t, LinearLayout ll)
    {
        super(t);
        pecasTabuleiro.add(new Torre(t, t.getPosicao(8,1),this,(LinearLayout) ll.getChildAt(7)));
        pecasTabuleiro.add(new Cavalo(t, t.getPosicao(8, 2),this, (LinearLayout) ll.getChildAt(7)));
        pecasTabuleiro.add(new Bispo(t, t.getPosicao(8, 3),this, (LinearLayout) ll.getChildAt(7)));
        pecasTabuleiro.add(new Rainha(t, t.getPosicao(8, 4),this, (LinearLayout) ll.getChildAt(7)));
        pecasTabuleiro.add(new Rei(t, t.getPosicao(8, 5),this, (LinearLayout) ll.getChildAt(7)));
        pecasTabuleiro.add(new Bispo(t, t.getPosicao(8, 6),this, (LinearLayout) ll.getChildAt(7)));
        pecasTabuleiro.add(new Cavalo(t, t.getPosicao(8, 7),this, (LinearLayout) ll.getChildAt(7)));
        pecasTabuleiro.add(new Torre(t, t.getPosicao(8, 8),this, (LinearLayout) ll.getChildAt(7)));

        for(int i=1;i<=TABULEIRO_COLUNAS;i++)
        {
            pecasTabuleiro.add(new Peao(t,t.getPosicao(7, i),this, (LinearLayout) ll.getChildAt(6)));
        }
    }
}
