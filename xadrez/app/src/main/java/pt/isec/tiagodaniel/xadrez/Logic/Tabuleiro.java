/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.LinearLayout;

import static java.lang.System.in;
import java.util.ArrayList;
import static pt.isec.tiagodaniel.xadrez.Logic.Constantes.*;

/**
 *
 * @author Tiago Coutinho
 */
public class Tabuleiro
{
    private ArrayList<Posicao> tabuleiro;
    private ArrayList<Jogador> jogadores;
    
    public Tabuleiro(LinearLayout ll)
    {
        tabuleiro=new ArrayList<Posicao>();
        
        for(int i=1;i<=TABULEIRO_LINHAS;i++)
        {
            for(int j=1;j<=TABULEIRO_COLUNAS;j++)
            {
                tabuleiro.add(new Posicao(i,j));
            }
        }
        jogadores=new ArrayList<Jogador>();
        jogadores.add(new Jogador1(this, ll));
        jogadores.add(new Jogador2(this, ll));
    }
    
    public boolean dentroLimites(int linha, int coluna)
    {
        return !(linha>TABULEIRO_LINHAS || coluna >TABULEIRO_COLUNAS);
    }            
    
    public boolean isOcupado(Posicao p)
    {
        return p.isOcupado();
    }
    
    public boolean isOcupado(int linha, int coluna)
    {
        if(!dentroLimites(linha, coluna))
            return false;
        
        for(Posicao p : tabuleiro)
        {
            if(p.getLinha()==linha && p.getColuna()==coluna)
                return p.isOcupado();
        }
        System.out.println("DEU ERRO!");
        return true;
    }
    
    public Posicao getPosicao(int linha, int coluna)
    {
        if(!dentroLimites(linha, coluna))
            return null;
        
        for(Posicao p : tabuleiro)
        {
            if(p.getLinha()==linha && p.getColuna()==coluna)
                return p;
        }
        int a=0;
        int b=0;
        return null;
    }
    
    public boolean podeComer(int linha, int coluna, Jogador j)
    {
        Posicao p;
        if((p=getPosicao(linha, coluna))==null)
            return false;
            
        return p.isOcupado() && p.getPeca().getJogador()!=j;
    }
    
    public void setCheck()
    {
        for(Jogador j : jogadores)
        {
            if(j.poeEmCheck())
                j.setCheck(true);
            else
                j.setCheck(false);
        }
    }
}
