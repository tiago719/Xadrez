package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.Chronometer;
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
        criaPecas();
    }

    public void criaPecas()
    {
        Peca p;
        Posicao posicao;
        pecasTabuleiro.add(p=new Torre(tabuleiro,this));
        (posicao=tabuleiro.getPosicao(1,'a')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Cavalo(tabuleiro,this));
        (posicao=tabuleiro.getPosicao(1,'b')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Bispo(tabuleiro,this));
        (posicao=tabuleiro.getPosicao(1,'c')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Rainha(tabuleiro,this));
        (posicao=tabuleiro.getPosicao(1,'d')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Rei(tabuleiro,this));
        (posicao=tabuleiro.getPosicao(1,'e')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Bispo(tabuleiro,this));
        (posicao=tabuleiro.getPosicao(1,'f')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Cavalo(tabuleiro,this));
        (posicao=tabuleiro.getPosicao(1,'g')).setPeca(p);
        posicao.desenhaPeca();
        pecasTabuleiro.add(p=new Torre(tabuleiro,this));
        (posicao=tabuleiro.getPosicao(1,'h')).setPeca(p);
        posicao.desenhaPeca();

        for(int i=0;i<TABULEIRO_COLUNAS;i++)
        {
            pecasTabuleiro.add(p=new Peao(tabuleiro,this));
            (posicao=tabuleiro.getPosicao(2,(char)('a'+i))).setPeca(p);
            posicao.desenhaPeca();
        }
    }
}
