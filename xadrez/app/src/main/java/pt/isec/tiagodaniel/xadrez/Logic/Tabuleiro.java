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
            for(int j=0;j<TABULEIRO_COLUNAS;j++)
            {
                tabuleiro.add(new Posicao(i,(char)('a'+j)));
            }
        }
        jogadores=new ArrayList<Jogador>();
        jogadores.add(new Jogador1(this, ll));
        jogadores.add(new Jogador2(this, ll));
    }
    
    public boolean dentroLimites(int linha, char coluna)
    {
        if(linha > TABULEIRO_LINHAS || linha<0 || (int)coluna >TABULEIRO_COLUNAS_SUP_CHAR_ASCII || (int)coluna <TABULEIRO_COLUNAS_INF_CHAR_ASCII)
            return false;
        return true;
    }            
    
    public boolean isOcupado(Posicao p)
    {
        return p.isOcupado();
    }
    
    public boolean isOcupado(int linha, char coluna)
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
    
    public Posicao getPosicao(int linha, char coluna)
    {
        if(!dentroLimites(linha, coluna))
            return null;
        
        for(Posicao p : tabuleiro)
        {
            if(p.getLinha()==linha && p.getColuna()==coluna)
                return p;
        }
        return null;
    }
    
    public boolean podeComer(int linha, char coluna, Jogador j)
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
