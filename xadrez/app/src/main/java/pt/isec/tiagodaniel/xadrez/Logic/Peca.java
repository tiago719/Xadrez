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
    protected String nome;

    public Peca(Tabuleiro tabuleiro, Jogador j, String nomePeca)
    {
        this.tabuleiro=tabuleiro;
        jogador=j;
        this.nome = nomePeca;
    }

    public abstract ArrayList<Posicao> verificaDisponiveisCheck();
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
    
    public boolean poeCheck(Jogador jogador)
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        disponiveis=verificaDisponiveisCheck();
        Peca peca;

        if(disponiveis!=null)
        {
            for (Posicao posicao : disponiveis)
                if ((peca = posicao.getPeca()) != null && peca.getJogador()==jogador)
                    if (peca instanceof Rei)
                    {
                        int a = 0;
                        return true;
                    }
        }
        return false;
    }

    public String getNomePeca() {
        return this.nome;
    }
}
