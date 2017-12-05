/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import java.util.ArrayList;

/**
 *
 * @author Tiago Coutinho
 */
public class Cavalo extends Peca
{
    public Cavalo(Tabuleiro t, Posicao p, Jogador j)
    {
        super(t,p,j);
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
}
