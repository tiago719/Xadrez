/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import java.util.ArrayList;

/**
 *
 * @author Tiago Coutinho
 */
public class Posicao
{
    private int linha;
    private int coluna;
    private Peca peca;
    
    public Posicao(int linha, int coluna)
    {
        this.linha=linha;
        this.coluna=coluna;
        peca=null;

    }
    
    public Posicao(int linha, int coluna, Peca p)
    {
        this.linha=linha;
        this.coluna=coluna;
        peca=p;
    }

    public int getLinha()
    {
        return linha;
    }

    public void setLinha(int linha)
    {
        this.linha = linha;
    }

    public int getColuna()
    {
        return coluna;
    }

    public void setColuna(int coluna)
    {
        this.coluna = coluna;
    }

    public Peca getPeca()
    {
        return peca;
    }

    public void setPeca(Peca peca)
    {
        this.peca = peca;
    }
    
    public boolean isOcupado()
    {
        return peca != null;
    }
}
