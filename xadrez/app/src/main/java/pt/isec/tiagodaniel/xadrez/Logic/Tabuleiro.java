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
    private Jogador jogadorAtual;
    
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
        jogadorAtual=jogadores.get(0);
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

    public void adiciona(ArrayList<Posicao> disponiveis, Posicao nova)
    {
        if(!nova.isOcupado())
            disponiveis.add(nova);
        else
        if(this.podeComer(nova.getLinha(), nova.getColuna(), this.jogadorAtual))
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
            if(this.podeComer(nova.getLinha(), nova.getColuna(), this.jogadorAtual))
                disponiveis.add(nova);
            return true;
        }
    }

    private Posicao encontraPeca(Peca peca)
    {
        for(Posicao posicao : tabuleiro)
        {
            if(posicao.getPeca()==peca)
                return posicao;
        }
        return null;
    }

    public ArrayList<Posicao> horizontalVertival(Peca peca)
    {
        Posicao posicao=encontraPeca(peca);
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao nova;

        for(int i=1;;i++)
        {
            nova=this.getPosicao(posicao.getLinha(),(char) (posicao.getColuna()+i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }
        for(int i=-1;;i--)
        {
            nova=this.getPosicao(posicao.getLinha(), (char) (posicao.getColuna()+i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }

        for(int i=1;;i++)
        {
            nova=this.getPosicao(posicao.getLinha()+i,(char) (posicao.getColuna()));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }

        for(int i=-1;;i--)
        {
            nova=this.getPosicao(posicao.getLinha()+i,(char) (posicao.getColuna()));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }

        return disponiveis;
    }

    public ArrayList<Posicao> cavalo(Peca peca)
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao p=encontraPeca(peca), nova;

        if((nova=this.getPosicao(p.getLinha()+2,(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()+2,(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-2,(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-2,(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()+1,(char) (p.getColuna()-2)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()+1,(char) (p.getColuna()+2)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()-2)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()+2)))!=null)
            adiciona(disponiveis, nova);

        return disponiveis;
    }

    public ArrayList<Posicao> peao(Peao peca)
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao p=encontraPeca(peca);
        Posicao nova;

        if(jogadorAtual instanceof Jogador1)
        {
            if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna()))) != null)
                if (!nova.isOcupado()) disponiveis.add(nova);

            if (peca.isPrimeiroLance())
                if ((nova = this.getPosicao(p.getLinha() + 2, (char) (p.getColuna()))) != null)
                    if (!nova.isOcupado()) disponiveis.add(nova);

            if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna() + 1))) != null)
                if (this.podeComer(nova.getLinha(), nova.getColuna(), jogadorAtual)) disponiveis.add(nova);

            if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna() + 1))) != null)
                if (this.podeComer(nova.getLinha(), nova.getColuna(), jogadorAtual)) disponiveis.add(nova);

            //TODO: Leis da FIDE: Pág 6, 3.7, d)
        }
        else
        {
            if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna()))) != null)
                if (!nova.isOcupado()) disponiveis.add(nova);

            if (peca.isPrimeiroLance())
                if ((nova = this.getPosicao(p.getLinha() - 2, (char) (p.getColuna()))) != null)
                    if (!nova.isOcupado()) disponiveis.add(nova);

            if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna() - 1))) != null)
                if (this.podeComer(nova.getLinha(), nova.getColuna(), this.jogadorAtual)) disponiveis.add(nova);

            if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna() - 1))) != null)
                if (this.podeComer(nova.getLinha(), nova.getColuna(), this.jogadorAtual)) disponiveis.add(nova);

            //TODO: Leis da FIDE: Pág 6, 3.7, d)
        }

        return disponiveis;
    }

    public ArrayList<Posicao> rei(Peca peca)
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao p=encontraPeca(peca), nova;

        if((nova=this.getPosicao(p.getLinha(),(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha(),(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna())))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()+1,(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova);

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna())))!=null)
            adiciona(disponiveis, nova);

        return disponiveis;
    }

    public ArrayList<Posicao> diagonal(Peca peca)
    {
        Posicao posicao=encontraPeca(peca);
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao nova;

        for(int i=1;;i++)
        {
            nova=this.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()+i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }
        for(int i=-1;;i--)
        {
            nova=this.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()+i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }

        for(int i=1;;i++)
        {
            nova=this.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()-i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }

        for(int i=-1;;i--)
        {
            nova=this.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()-i));
            if(nova==null)
                return disponiveis;
            if(ultima(disponiveis,nova))
                break;
        }

        return disponiveis;
    }

    private Jogador getJogadorAdversario()
    {
        for(Jogador j : jogadores)
            if(j!=jogadorAtual)
                return j;

        return null;
    }

    public void movePara(Posicao posicaoOrigem, Posicao posicaoDestino)
    {
        if(posicaoDestino.isOcupado())
        {
            getJogadorAdversario().addPecaMorta(posicaoDestino.getPeca());
        }
    }
}
