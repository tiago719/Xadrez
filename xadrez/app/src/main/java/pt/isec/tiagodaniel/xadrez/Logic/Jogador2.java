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
        Peca p;
        pecasTabuleiro.add((p =new Torre(t, t.getPosicao(1,'a'),this, (LinearLayout) ll.getChildAt(7))));
        t.getPosicao(1,'a').setPeca(p);
        pecasTabuleiro.add((p =new Cavalo(t, t.getPosicao(1, 'b'),this, (LinearLayout) ll.getChildAt(7))));
        t.getPosicao(1,'b').setPeca(p);
        pecasTabuleiro.add((p =new Bispo(t, t.getPosicao(1, 'c'),this, (LinearLayout) ll.getChildAt(7))));
        t.getPosicao(1,'c').setPeca(p);
        pecasTabuleiro.add((p =new Rainha(t, t.getPosicao(1, 'd'),this, (LinearLayout) ll.getChildAt(7))));
        t.getPosicao(1,'d').setPeca(p);
        pecasTabuleiro.add((p =new Rei(t, t.getPosicao(1, 'e'),this, (LinearLayout) ll.getChildAt(7))));
        t.getPosicao(1,'e').setPeca(p);
        pecasTabuleiro.add((p =new Bispo(t, t.getPosicao(1, 'f'),this, (LinearLayout) ll.getChildAt(7))));
        t.getPosicao(1,'f').setPeca(p);
        pecasTabuleiro.add((p =new Cavalo(t, t.getPosicao(1, 'g'),this, (LinearLayout) ll.getChildAt(7))));
        t.getPosicao(1,'g').setPeca(p);
        pecasTabuleiro.add((p =new Torre(t, t.getPosicao(1, 'h'),this, (LinearLayout) ll.getChildAt(7))));
        t.getPosicao(1,'h').setPeca(p);

        for(int i=0;i<TABULEIRO_COLUNAS;i++)
        {
            pecasTabuleiro.add((p =new Peao(t,t.getPosicao(2, (char)('a'+i)),this,(LinearLayout) ll.getChildAt(6))));
            t.getPosicao(2,(char)('a'+i)).setPeca(p);
        }
    }
}
