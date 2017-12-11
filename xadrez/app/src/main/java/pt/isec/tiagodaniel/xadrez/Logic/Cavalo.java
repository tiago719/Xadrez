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
    public Cavalo(Tabuleiro tabuleiro, Jogador j, ImageView ll)
    {
        super(tabuleiro,j,ll);
    }
    
    @Override
    public ArrayList<Posicao> getDisponiveis()
    {
       return tabuleiro.cavalo(this);
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
