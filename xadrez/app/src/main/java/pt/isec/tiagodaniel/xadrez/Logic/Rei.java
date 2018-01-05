/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;

import pt.isec.tiagodaniel.xadrez.R;

/**
 *
 * @author Tiago Coutinho
 */
public class Rei extends Peca implements Constantes
{
    private boolean movido;
    public Rei(Tabuleiro tabuleiro, Jogador j)
    {
        super(tabuleiro,j, REI);
        movido=false;
    }

    @Override
    public ArrayList<Posicao> getDisponiveis(Jogador atual, boolean pc)
    {
        ArrayList<Posicao> disponiveis= tabuleiro.rei(this, pc);

        for (Iterator<Posicao> iterator = disponiveis.iterator(); iterator.hasNext();) {
            Posicao posicao = iterator.next();
            if(atual.ficaEmCheck(posicao, this))
            {
                iterator.remove();
            }
        }
        return disponiveis;
    }

    @Override
    public ArrayList<Posicao> verificaDisponiveisCheck()
    {
        return tabuleiro.rei(this, false);
    }

    @Override
    public void desenhaPeca(ImageView childAt)
    {
        if(jogador instanceof JogadorLight)
            childAt.setImageResource(R.drawable.b_rei);
        else
            childAt.setImageResource(R.drawable.p_rei);
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
