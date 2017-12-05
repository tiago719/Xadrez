/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import java.util.ArrayList;
import static pt.isec.tiagodaniel.xadrez.Logic.Constantes.*;

/**
 *
 * @author Tiago Coutinho
 */
public class Jogador
{
    private ArrayList<Peca> pecasTabuleiro;
    private ArrayList<Peca> pecasMortas;
    private boolean check;
    
    public Jogador(Tabuleiro t)
    {
        pecasTabuleiro=new ArrayList<Peca>();
        
        pecasTabuleiro.add(new Torre(t, t.getPosicao(0,0),this));
        pecasTabuleiro.add(new Cavalo(t, t.getPosicao(0, 1),this));
        pecasTabuleiro.add(new Bispo(t, t.getPosicao(0, 2),this));
        pecasTabuleiro.add(new Rei(t, t.getPosicao(0, 3),this));
        pecasTabuleiro.add(new Rainha(t, t.getPosicao(0, 4),this));
        pecasTabuleiro.add(new Bispo(t, t.getPosicao(0, 5),this));
        pecasTabuleiro.add(new Cavalo(t, t.getPosicao(0, 6),this));
        pecasTabuleiro.add(new Torre(t, t.getPosicao(0, 7),this));
        
        for(int i=0;i<TABULEIRO_COLUNAS;i++)
        {
            pecasTabuleiro.add(new Peao(t,t.getPosicao(1, i),this));
        }
        
        pecasMortas=new ArrayList<Peca>();
        check=false;
    }

    public boolean isCheck()
    {
        return check;
    }

    public void setCheck(boolean check)
    {
        this.check = check;
    }
    
    

    public void addPecaMorta(Peca p)
    {
        pecasMortas.add(p);
    }
    
    public void joga()
    {
        int randomNum = 0 + (int)(Math.random() * pecasTabuleiro.size());
        
        Peca peca=pecasTabuleiro.get(randomNum);
        
        ArrayList<Posicao> disponiveis=peca.getDisponiveis();
        
        randomNum=0 + (int)(Math.random() * disponiveis.size());
        
        peca.movePara(disponiveis.get(randomNum));
    }
    
    public boolean poeEmCheck()
    {
        for(Peca p : pecasTabuleiro)
            if(p.poeCheck())
                return true;
        return false;
    }
}
