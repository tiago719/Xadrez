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
    private char coluna;
    private Peca peca;
    
    public Posicao(int linha, char coluna)
    {
        this.linha=linha;
        this.coluna=coluna;
        peca=null;

    }
    
    public Posicao(int linha, char coluna, Peca p)
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

    public char getColuna()
    {
        return coluna;
    }

    public int getColunaNum()
    {
        switch (coluna)
        {
            case 'a':
                return 1;
            case 'b':
                return 2;
            case 'c':
                return 3;
            case 'd':
                return 4;
            case 'e':
                return 5;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'h':
                return 8;
        }
      return -1;
    }

    public void setColuna(char coluna)
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
