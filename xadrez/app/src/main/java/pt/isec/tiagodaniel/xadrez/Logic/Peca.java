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
    protected Jogador jogador;
    protected Tabuleiro tabuleiro;
    
    public Peca(Tabuleiro tabuleiro, Jogador j, ImageView ll)
    {
        this.tabuleiro=tabuleiro;
        jogador=j;
        desenhaPeca(ll);
    }

    public abstract ArrayList<Posicao> getDisponiveis();

    public abstract void desenhaPeca(ImageView childAt);

    public Jogador getJogador()
    {
        return jogador;
    }

    public void setJogador(Jogador jogador)
    {
        this.jogador = jogador;
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
