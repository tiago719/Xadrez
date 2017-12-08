/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.R;

/**
 *
 * @author Tiago Coutinho
 */
public abstract class Peca
{
    protected Tabuleiro tabuleiro;
    protected Posicao posicao;
    protected Jogador jogador;
    
    public Peca(Tabuleiro t, Posicao p, Jogador j, LinearLayout ll)
    {
        tabuleiro=t;
        posicao=p;
        jogador=j;
        desenhaPeca((ImageView) ll.getChildAt((p.getColunaNum()-1)));
    }

    public Posicao getPosicao()
    {
        return posicao;
    }

    public void setPosicao(Posicao posicao)
    {
        this.posicao = posicao;
    }

    public abstract ArrayList<Posicao> getDisponiveis();

    public abstract void desenhaPeca(ImageView childAt);

    public Tabuleiro getTabuleiro()
    {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro)
    {
        this.tabuleiro = tabuleiro;
    }

    public Jogador getJogador()
    {
        return jogador;
    }

    public void setJogador(Jogador jogador)
    {
        this.jogador = jogador;
    }

    public void adiciona(ArrayList<Posicao> disponiveis, Posicao nova)
    {
        if(!nova.isOcupado())
            disponiveis.add(nova);
        else
            if(getTabuleiro().podeComer(nova.getLinha(), nova.getColuna(), getPosicao().getPeca().getJogador()))
                disponiveis.add(nova);
    }
    
    public boolean ultima(ArrayList<Posicao> disponiveis, Posicao nova)
    {
        if(nova==null)
            return false;

        if(!nova.isOcupado())
        {
            disponiveis.add(nova);
            return false;
        }
        else
        {
            if(getTabuleiro().podeComer(nova.getLinha(), nova.getColuna(), getPosicao().getPeca().getJogador()))
                disponiveis.add(nova);
            return true;
        }
    }
    
    public ArrayList<Posicao> horizontalVertival()
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao nova;
        
        for(int i=1;;i++)
        {
            nova=tabuleiro.getPosicao(posicao.getLinha(),(char) (posicao.getColuna()+i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }
        for(int i=-1;;i--)
        {
            nova=tabuleiro.getPosicao(posicao.getLinha(), (char) (posicao.getColuna()+i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }

        for(int i=1;;i++)
        {
            nova=tabuleiro.getPosicao(posicao.getLinha()+i,(char) (posicao.getColuna()));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }
        
        for(int i=-1;;i--)
        {
            nova=tabuleiro.getPosicao(posicao.getLinha()+i,(char) (posicao.getColuna()));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }
        
        return disponiveis;
    }
    
    public ArrayList<Posicao> diagonal()
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao nova;

        for(int i=1;;i++)
        {
            nova=tabuleiro.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()+i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }
        for(int i=-1;;i--)
        {
            nova=tabuleiro.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()+i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }

        for(int i=1;;i++)
        {
            nova=tabuleiro.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()-i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }
        
        for(int i=-1;;i--)
        {
            nova=tabuleiro.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()-i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }
        
        return disponiveis;
    }
    
    public void movePara(Posicao posicao)
    {
        if(posicao.isOcupado())
        {
            jogador.addPecaMorta(posicao.getPeca());
        }
        
        setPosicao(posicao);
    }
    
    public boolean poeCheck()
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        disponiveis=getDisponiveis();
        Peca peca;
        
        for(Posicao posicao : disponiveis)
            if((peca=posicao.getPeca())!=null)
                if(peca instanceof Rei)
                    return true;
        
        return false;
    }
}
