package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.ImageView;
import android.widget.LinearLayout;

import static pt.isec.tiagodaniel.xadrez.Logic.Constantes.TABULEIRO_COLUNAS;

/**
 * Created by Tiago Coutinho on 05/12/2017.
 */

public class JogadorLight extends Jogador
{
    public JogadorLight(Tabuleiro t)
    {
        super(t);
        Peca p;
        Posicao posicao;
        pecasTabuleiro.add(p=new Torre(t,this));
        (posicao=t.getPosicao(1,'a')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Cavalo(t,this));
        (posicao=t.getPosicao(1,'b')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Bispo(t,this));
        (posicao=t.getPosicao(1,'c')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Rainha(t,this));
        (posicao=t.getPosicao(1,'d')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Rei(t,this));
        (posicao=t.getPosicao(1,'e')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Bispo(t,this));
        (posicao=t.getPosicao(1,'f')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Cavalo(t,this));
        (posicao=t.getPosicao(1,'g')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Torre(t,this));
        (posicao=t.getPosicao(1,'h')).setPeca(p);
        posicao.desenhaPeca();

        for(int i=0;i<TABULEIRO_COLUNAS;i++)
        {
            pecasTabuleiro.add(p=new Peao(t,this));
            (posicao=t.getPosicao(2,(char)('a'+i))).setPeca(p);
            posicao.desenhaPeca();
        }
    }
}
