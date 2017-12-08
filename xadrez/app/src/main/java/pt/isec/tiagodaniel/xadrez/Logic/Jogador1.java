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
        Peca p;
        pecasTabuleiro.add((p =new Torre(t, t.getPosicao(8,'a'),this, (LinearLayout) ll.getChildAt(0))));
        t.getPosicao(8,'a').setPeca(p);
        pecasTabuleiro.add((p =new Cavalo(t, t.getPosicao(8, 'b'),this, (LinearLayout) ll.getChildAt(0))));
        t.getPosicao(8,'b').setPeca(p);
        pecasTabuleiro.add((p =new Bispo(t, t.getPosicao(8, 'c'),this, (LinearLayout) ll.getChildAt(0))));
        t.getPosicao(8,'c').setPeca(p);
        pecasTabuleiro.add((p =new Rainha(t, t.getPosicao(8, 'd'),this, (LinearLayout) ll.getChildAt(0))));
        t.getPosicao(8,'d').setPeca(p);
        pecasTabuleiro.add((p =new Rei(t, t.getPosicao(8, 'e'),this, (LinearLayout) ll.getChildAt(0))));
        t.getPosicao(8,'e').setPeca(p);
        pecasTabuleiro.add((p =new Bispo(t, t.getPosicao(8, 'f'),this, (LinearLayout) ll.getChildAt(0))));
        t.getPosicao(8,'f').setPeca(p);
        pecasTabuleiro.add((p =new Cavalo(t, t.getPosicao(8, 'g'),this, (LinearLayout) ll.getChildAt(0))));
        t.getPosicao(8,'g').setPeca(p);
        pecasTabuleiro.add((p =new Torre(t, t.getPosicao(8, 'h'),this, (LinearLayout) ll.getChildAt(0))));
        t.getPosicao(8,'h').setPeca(p);

        for(int i=0;i<TABULEIRO_COLUNAS;i++)
        {
            pecasTabuleiro.add((p =new Peao(t,t.getPosicao(7, (char)('a'+i)),this,(LinearLayout) ll.getChildAt(1))));
            t.getPosicao(7,(char)('a'+i)).setPeca(p);
        }
    }
}
