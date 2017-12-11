package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.ImageView;
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
        pecasTabuleiro.add(p=new Torre(t,this, (ImageView)((LinearLayout) ll.getChildAt(7)).getChildAt(0)));
        t.getPosicao(8,'a').setPeca(p);
        pecasTabuleiro.add(p=new Cavalo(t,this, (ImageView)((LinearLayout) ll.getChildAt(7)).getChildAt(1)));
        t.getPosicao(8,'b').setPeca(p);
        pecasTabuleiro.add(p=new Bispo(t,this, (ImageView)((LinearLayout) ll.getChildAt(7)).getChildAt(2)));
        t.getPosicao(8,'c').setPeca(p);
        pecasTabuleiro.add(p=new Rainha(t,this, (ImageView)((LinearLayout) ll.getChildAt(7)).getChildAt(3)));
        t.getPosicao(8,'d').setPeca(p);
        pecasTabuleiro.add(p=new Rei(t,this, (ImageView)((LinearLayout) ll.getChildAt(7)).getChildAt(4)));
        t.getPosicao(8,'e').setPeca(p);
        pecasTabuleiro.add(p=new Bispo(t,this, (ImageView)((LinearLayout) ll.getChildAt(7)).getChildAt(5)));
        t.getPosicao(8,'f').setPeca(p);
        pecasTabuleiro.add(p=new Cavalo(t,this, (ImageView)((LinearLayout) ll.getChildAt(7)).getChildAt(6)));
        t.getPosicao(8,'g').setPeca(p);
        pecasTabuleiro.add(p=new Torre(t,this, (ImageView)((LinearLayout) ll.getChildAt(7)).getChildAt(7)));
        t.getPosicao(8,'h').setPeca(p);

        for(int i=0;i<TABULEIRO_COLUNAS;i++)
        {
            pecasTabuleiro.add(p=new Peao(t,this,(ImageView)((LinearLayout) ll.getChildAt(6)).getChildAt(i)));
            t.getPosicao(7,(char)('a'+i)).setPeca(p);
        }
    }
}
