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
public class Rei extends Peca
{
    public Rei(Tabuleiro tabuleiro, Jogador j, ImageView ll)
    {
        super(tabuleiro,j,ll);
    }
    
    @Override
    public ArrayList<Posicao> getDisponiveis()
    {
        return tabuleiro.rei(this);
    }

    @Override
    public void desenhaPeca(ImageView childAt)
    {
        if(jogador instanceof Jogador1)
            childAt.setImageResource(R.drawable.b_rei);
        else
            childAt.setImageResource(R.drawable.p_rei);
    }
}
