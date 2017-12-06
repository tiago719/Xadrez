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
public class Torre extends Peca
{
    public Torre(Tabuleiro t, Posicao p, Jogador j, LinearLayout ll)
    {
        super(t,p,j,ll);
    }

    @Override
    public ArrayList<Posicao> getDisponiveis()
    {
        return horizontalVertival();
    }

    @Override
    public void desenhaPeca(ImageView childAt)
    {
        if(jogador instanceof Jogador1)
            childAt.setImageResource(R.drawable.b_torre);
        else
            childAt.setImageResource(R.drawable.p_torre);
    }
}
