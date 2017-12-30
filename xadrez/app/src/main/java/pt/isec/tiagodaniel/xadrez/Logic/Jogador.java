/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.os.Handler;
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
    protected Tabuleiro tabuleiro;
    
    public Jogador(Tabuleiro t)
    {
        pecasTabuleiro=new ArrayList<Peca>();
        pecasMortas=new ArrayList<Peca>();

        check=false;
        tabuleiro=t;
    }

    public boolean isCheck()
    {
        return check;
    }

    public void setCheck(boolean check)
    {
        this.check = check;
    }

    public ArrayList<Peca> getPecasTabuleiro()
    {
        return pecasTabuleiro;
    }

    public void setPecasTabuleiro(ArrayList<Peca> pecasTabuleiro)
    {
        this.pecasTabuleiro = pecasTabuleiro;
    }

    public void verificaCheck()
    {
        Jogador jogador=tabuleiro.getJogadorAdversarioPeca(pecasTabuleiro.get(0));

        for(Peca peca :jogador.getPecasTabuleiro())
            if(peca.poeCheck(this))
            {
                check = true;
                return;
            }
        check=false;
    }

    public void addPecaMorta(Peca p)
    {
        pecasMortas.add(p);
        pecasTabuleiro.remove(p);
    }

    public void addPeca(Peca p)
    {
        pecasTabuleiro.add(p);
    }
    
    public void joga()
    {
        int randomNum;
        Peca peca;

        do
        {
            randomNum = 0 + (int)(Math.random() * pecasTabuleiro.size());
            peca=pecasTabuleiro.get(randomNum);

        }while(peca.getDisponiveis().size()==0);

        ArrayList<Posicao> disponiveis=peca.getDisponiveis();
        
        randomNum=0 + (int)(Math.random() * disponiveis.size());

        DelayMovePara delayMovePara = new DelayMovePara(peca, disponiveis, randomNum);
        new Handler().postDelayed(delayMovePara, 1000);
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

    public class DelayMovePara implements Runnable {
        private Peca peca;
        private ArrayList<Posicao> disponiveis;
        private int randomNum;

        public DelayMovePara(Peca peca, ArrayList<Posicao> disponiveis, int randomNum) {
            this.peca = peca;
            this.disponiveis = disponiveis;
            this.randomNum = randomNum;
        }

        @Override
        public void run() {
            tabuleiro.movePara(tabuleiro.encontraPeca(peca), disponiveis.get(randomNum));
        }
    }
}
