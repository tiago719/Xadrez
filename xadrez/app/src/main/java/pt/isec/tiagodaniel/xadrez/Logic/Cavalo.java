/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.R;

/**
 *
 * @author Tiago Coutinho
 */
public class Cavalo extends Peca
{
    public Cavalo(Tabuleiro t, Posicao p, Jogador j, LinearLayout ll)
    {
        super(t,p,j,ll);
    }
    
    @Override
    public ArrayList<Posicao> getDisponiveis()
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao p=getPosicao(), nova;
        Tabuleiro t=getTabuleiro();
        
        if((nova=t.getPosicao(p.getLinha()+2,p.getColuna()+1))!=null)
            adiciona(disponiveis, nova);
        
        if((nova=t.getPosicao(p.getLinha()+2,p.getColuna()-1))!=null)
            adiciona(disponiveis, nova);
        
        if((nova=t.getPosicao(p.getLinha()-2,p.getColuna()+1))!=null)
            adiciona(disponiveis, nova);
        
        if((nova=t.getPosicao(p.getLinha()-2,p.getColuna()-1))!=null)
            adiciona(disponiveis, nova);
        
        if((nova=t.getPosicao(p.getLinha()+1,p.getColuna()-2))!=null)
            adiciona(disponiveis, nova);
        
        if((nova=t.getPosicao(p.getLinha()+1,p.getColuna()+2))!=null)
            adiciona(disponiveis, nova);
        
        if((nova=t.getPosicao(p.getLinha()-1,p.getColuna()-2))!=null)
            adiciona(disponiveis, nova);
        
        if((nova=t.getPosicao(p.getLinha()-1,p.getColuna()+2))!=null)
            adiciona(disponiveis, nova);
                           
        return disponiveis;
    }

    @Override
    public void desenhaPeca(ImageView childAt)
    {
        if(jogador instanceof Jogador1)
            childAt.setImageResource(R.drawable.b_cavalo);
        else
            childAt.setImageResource(R.drawable.p_cavalo);
    }
}
