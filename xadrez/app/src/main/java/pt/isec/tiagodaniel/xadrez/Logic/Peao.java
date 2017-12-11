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
    
    public Peao(Tabuleiro tabuleiro, Jogador j, ImageView ll)
    {
        super(tabuleiro,j,ll);
    }
    
    public ArrayList<Posicao> getDisponiveis()
    {
        return tabuleiro.peao(this);
    }

    public boolean isPrimeiroLance()
    {
        return primeiroLance;
    }

    public void setPrimeiroLance(boolean primeiroLance)
    {
        this.primeiroLance = primeiroLance;
    }

    @Override
    public void desenhaPeca(ImageView childAt)
    {
        if(jogador instanceof Jogador1)
            childAt.setImageResource(R.drawable.b_peao);
        else
            childAt.setImageResource(R.drawable.p_peao);
    }
}
