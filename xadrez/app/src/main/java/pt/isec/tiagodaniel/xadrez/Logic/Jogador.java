/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.LinearLayout;

import java.util.ArrayList;
import static pt.isec.tiagodaniel.xadrez.Logic.Constantes.*;

/**
 *
 * @author Tiago Coutinho
 */
public class Jogador
{
    protected ArrayList<Peca> pecasTabuleiro;
    protected ArrayList<Peca> pecasMortas;
    protected boolean check;
    
    public Jogador(Tabuleiro t)
    {
        pecasTabuleiro=new ArrayList<Peca>();
        pecasMortas=new ArrayList<Peca>();

        check=false;
    }

    public ArrayList<Peca> getPecasTabuleiro()
    {
        return pecasTabuleiro;
    }

    public void setPecasTabuleiro(ArrayList<Peca> pecasTabuleiro)
    {
        this.pecasTabuleiro = pecasTabuleiro;
    }

    public boolean poeCheck()
    {
        for(Peca p : pecasTabuleiro)
            if(p.poeCheck())
                return true;
        return false;
    }

    public void addPecaMorta(Peca p)
    {
        pecasMortas.add(p);
        pecasTabuleiro.remove(p);
    }
    
    public void joga()
    {
        int randomNum = 0 + (int)(Math.random() * pecasTabuleiro.size());
        
        Peca peca=pecasTabuleiro.get(randomNum);
        
        ArrayList<Posicao> disponiveis=peca.getDisponiveis();
        
        randomNum=0 + (int)(Math.random() * disponiveis.size());
        
        //peca.movePara(disponiveis.get(randomNum));
    }

    public boolean hasMovimentos()
    {
        for(Peca peca : pecasTabuleiro)
        {
            if(peca.getDisponiveis().size()>0)
                return true;
        }
        return false;
    }
}
