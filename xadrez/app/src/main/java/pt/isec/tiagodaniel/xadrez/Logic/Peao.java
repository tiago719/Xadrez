/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.R;

/**
 *
 * @author Tiago Coutinho
 */
public class Peao extends Peca
{
    private boolean primeiroLance=true;
    
    public Peao(Tabuleiro t, Posicao p, Jogador j, LinearLayout ll)
    {
        super(t,p,j,ll);
    }
    
    public ArrayList<Posicao> getDisponiveis()
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao p;
        Tabuleiro t=getTabuleiro();;
        
        if((p=t.getPosicao(getPosicao().getLinha(),getPosicao().getColuna()+1))!=null)
            if(!p.isOcupado())
                disponiveis.add(p);
        
        if(primeiroLance)
            if((p=t.getPosicao(getPosicao().getLinha(),getPosicao().getColuna()+2))!=null)
                if(!p.isOcupado())
                    disponiveis.add(p);
        
        if((p=t.getPosicao(getPosicao().getLinha()+1,getPosicao().getColuna()+1))!=null)
            if(t.podeComer(p.getLinha(),p.getColuna(), getJogador()))
                disponiveis.add(p);
        
        if((p=t.getPosicao(getPosicao().getLinha()+1,getPosicao().getColuna()-1))!=null)
            if(t.podeComer(p.getLinha(),p.getColuna(), getJogador()))
                disponiveis.add(p);
        
        //TODO: Leis da FIDE: Pág 6, 3.7, d)
        
        return disponiveis;
    }

    @Override
    public void desenhaPeca(ImageView childAt)
    {
        if(jogador instanceof Jogador1)
            childAt.setImageResource(R.drawable.b_peao);
        else
            childAt.setImageResource(R.drawable.p_peao);
    }

    @Override
    public void movePara(Posicao posicao)
    {
        //TODO: Leis da FIDE: Pág 6, 3.7, d)
        
        super.movePara(posicao);
    }
}
