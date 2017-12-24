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
public class Torre extends Peca
{
    private boolean movido;

    public Torre(Tabuleiro tabuleiro, Jogador j)
    {
        super(tabuleiro,j);
        movido=false;
    }

    @Override
    public ArrayList<Posicao> getDisponiveis()
    {
        return tabuleiro.horizontalVertival(this);
    }

    @Override
    public void desenhaPeca(ImageView childAt)
    {
        if(jogador instanceof JogadorLight)
            childAt.setImageResource(R.drawable.b_torre);
        else
            childAt.setImageResource(R.drawable.p_torre);
    }

    public boolean isMovido()
    {
        return movido;
    }

    public void setMovido(boolean movido)
    {
        this.movido = movido;
    }
}
