/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.ImageView;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.R;

/**
 *
 * @author Tiago Coutinho
 */
public class Rainha extends Peca
{
    public Rainha(Tabuleiro tabuleiro, Jogador j)
    {
        super(tabuleiro,j);
    }
    
    @Override
    public ArrayList<Posicao> getDisponiveis()
    {
        ArrayList<Posicao> disponiveis=tabuleiro.horizontalVertival(this);
        
        if(!disponiveis.addAll(tabuleiro.diagonal(this)))
            System.out.println("ERRO A SOMAR OS DIAGONAIS COM OS VERTICAIS/HORIZONTAIS NA RAINHA");
        return disponiveis;
    }

    @Override
    public void desenhaPeca(ImageView childAt)
    {
        if(jogador instanceof JogadorLight)
            childAt.setImageResource(R.drawable.b_rainha);
        else
            childAt.setImageResource(R.drawable.p_rainha);
    }
}
