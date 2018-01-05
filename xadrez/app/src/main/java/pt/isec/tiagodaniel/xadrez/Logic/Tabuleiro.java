/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.isec.tiagodaniel.xadrez.Logic;

import android.app.Activity;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Date;

import pt.isec.tiagodaniel.xadrez.Logic.Historico.Historico;
import pt.isec.tiagodaniel.xadrez.Logic.Historico.Jogada;

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
    private Jogador jogadorAdversario;
    private Historico mHistorico;

    public Tabuleiro(LinearLayout ll, Chronometer chronometer1, Chronometer chronometer2)
    {
        tabuleiro=new ArrayList<Posicao>();
        this.mHistorico = new Historico(new Date());

        for(int i=1;i<=TABULEIRO_LINHAS;i++)
        {
            for(int j=0;j<TABULEIRO_COLUNAS;j++)
            {
                tabuleiro.add(new Posicao(i,(char)('a'+j), (ImageView)((LinearLayout) ll.getChildAt(8-i)).getChildAt(j)));
            }
        }
        jogadores=new ArrayList<Jogador>();
        jogadores.add(new JogadorDark(this));
        jogadores.add(new JogadorLight(this));
        jogadorAtual=jogadores.get(1);
        jogadorAdversario=jogadores.get(0);
    }
    
    public boolean dentroLimites(int linha, char coluna)
    {
        if(linha > TABULEIRO_LINHAS || linha<0 || (int)coluna >TABULEIRO_COLUNAS_SUP_CHAR_ASCII || (int)coluna <TABULEIRO_COLUNAS_INF_CHAR_ASCII)
            return false;
        return true;
    }

    public Jogador getJogadorAdversario()
    {
        return jogadorAdversario;
    }

    public void setJogadorAdversario(Jogador jogadorAdversario)
    {
        this.jogadorAdversario = jogadorAdversario;
    }

    public boolean isOcupado(Posicao p)
    {
        return p.isOcupado();
    }
    
    public boolean isOcupado(int linha, char coluna)
    {
        if(!dentroLimites(linha, coluna))
            return true;
        
        for(Posicao p : tabuleiro)
        {
            if(p.getLinha()==linha && p.getColuna()==coluna)
                return p.isOcupado();
        }
        System.out.println("DEU ERRO!");
        return true;
    }

    public boolean ficaEmCheckJogadorAtual(Posicao posicao, Peca peca)
    {
        Peca temp=posicao.getPeca();
        Posicao original=encontraPeca(peca);
        posicao.setPeca(peca);
        original.setPeca(null);

        for(Peca atual : jogadorAdversario.getPecasTabuleiro())
        {
            if(atual==temp)
                continue;
            if(atual.poeCheck(jogadorAtual))
            {
                original.setPeca(peca);
                posicao.setPeca(temp);
                return true;
            }
        }
        original.setPeca(peca);
        posicao.setPeca(temp);
        return false;
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

    public void adiciona(ArrayList<Posicao> disponiveis, Posicao nova, Jogador jogador)
    {
        if(!nova.isOcupado())
            disponiveis.add(nova);
        else if(this.podeComer(nova.getLinha(), nova.getColuna(), jogador))
            disponiveis.add(nova);
    }
    public boolean ultima(ArrayList<Posicao> disponiveis, Posicao nova, Jogador jogador)
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
            if(this.podeComer(nova.getLinha(), nova.getColuna(), jogador))
                disponiveis.add(nova);
            return true;
        }
    }

    public Posicao encontraPeca(Peca peca)
    {
        int cont=0;
        for(Posicao posicao : tabuleiro)
        {
            if(posicao.getPeca()!=null)
                cont++;
        }
        int a;
        if(cont!=32)
        {
            a = 10;
            a++;
        }

        for(Posicao posicao : tabuleiro)
        {
            if(posicao.getPeca()==peca)
                return posicao;
        }
        return null;
    }

    public ArrayList<Posicao> horizontalVertival(Peca peca, boolean pc)
    {
        Posicao posicao=encontraPeca(peca);
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao nova;

        for(int i=1;;i++)
        {
            nova=this.getPosicao(posicao.getLinha(),(char) (posicao.getColuna()+i));
            if(nova==null)
                break;
            if(ultima(disponiveis,nova, peca.getJogador()))
                break;
        }
        for(int i=-1;;i--)
        {
            nova=this.getPosicao(posicao.getLinha(), (char) (posicao.getColuna()+i));
            if(nova==null)
                break;
            if(ultima(disponiveis,nova, peca.getJogador()))
                break;
        }

        for(int i=1;;i++)
        {
            nova=this.getPosicao(posicao.getLinha()+i,(char) (posicao.getColuna()));
            if(nova==null)
                break;
            if(ultima(disponiveis,nova, peca.getJogador()))
                break;
        }

        for(int i=-1;;i--)
        {
            nova=this.getPosicao(posicao.getLinha()+i,(char) (posicao.getColuna()));
            if(nova==null)
                break;
            if(ultima(disponiveis,nova, peca.getJogador()))
                break;
        }

        return disponiveis;
    }

    public ArrayList<Posicao> cavalo(Peca peca, boolean pc)
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao p=encontraPeca(peca), nova;

        if(p==null)
            return disponiveis;

        if((nova=this.getPosicao(p.getLinha()+2,(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova, peca.getJogador());

        if((nova=this.getPosicao(p.getLinha()+2,(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova, peca.getJogador());

        if((nova=this.getPosicao(p.getLinha()-2,(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova, peca.getJogador());

        if((nova=this.getPosicao(p.getLinha()-2,(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova, peca.getJogador());

        if((nova=this.getPosicao(p.getLinha()+1,(char) (p.getColuna()-2)))!=null)
            adiciona(disponiveis, nova, peca.getJogador());

        if((nova=this.getPosicao(p.getLinha()+1,(char) (p.getColuna()+2)))!=null)
            adiciona(disponiveis, nova, peca.getJogador());

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()-2)))!=null)
            adiciona(disponiveis, nova, peca.getJogador());

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()+2)))!=null)
            adiciona(disponiveis, nova, peca.getJogador());

        return disponiveis;
    }

    public ArrayList<Posicao> peaoVerificaCheck(Peao peca)
     {
            boolean flag=false;
            ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
            Posicao p=encontraPeca(peca);
            if(p==null || peca==null)
                    return disponiveis;
            Posicao nova;

                    if(peca.getJogador() instanceof JogadorLight)
                {
                            if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna()))) != null)
                    {
                                if (!nova.isOcupado())
                        {
                                    disponiveis.add(nova);
                        flag=true;
                    }
                }

                        if (peca.isPrimeiroLance() && flag)
                        if ((nova = this.getPosicao(p.getLinha() + 2, (char) (p.getColuna()))) != null)
                            if (!nova.isOcupado()) disponiveis.add(nova);

                        if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna() + 1))) != null)
                        if (p.getPeca()!=null && this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()))
                        {
                                    nova.setEnPassant(false);
                        disponiveis.add(nova);
                    }

                        if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna() - 1))) != null)
                        if (p.getPeca()!=null && this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()))
                        {
                                    nova.setEnPassant(false);
                        disponiveis.add(nova);
                   }
            }
            else
            {
                        if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna()))) != null)
                    {
                                if (!nova.isOcupado())
                        {
                                    disponiveis.add(nova);
                        flag = true;
                    }
                }

                        if (peca.isPrimeiroLance() && flag)
                        if ((nova = this.getPosicao(p.getLinha() - 2, (char) (p.getColuna()))) != null)
                            if (!nova.isOcupado()) disponiveis.add(nova);

                        if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna() + 1))) != null)
                        if (p.getPeca()!=null && this.podeComer(nova.getLinha(), nova.getColuna(), p.getPeca().getJogador()))
                        {
                                    nova.setEnPassant(false);
                        disponiveis.add(nova);
                    }

                        if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna() - 1))) != null)
                        if (p.getPeca()!=null &&this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()))
                        {
                                    nova.setEnPassant(false);
                        disponiveis.add(nova);
                    }
           }
            return disponiveis;
        }

    public ArrayList<Posicao> peao(Peao peca, boolean pc)
    {
        boolean flag=false;
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao p=encontraPeca(peca);
        if(p==null || peca==null)
            return disponiveis;
        Posicao nova;

        if(peca.getJogador() instanceof JogadorLight)
        {
            if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna()))) != null)
            {
                if (!nova.isOcupado())
                {
                    disponiveis.add(nova);
                    flag=true;
                }
            }

            if (peca.isPrimeiroLance() && flag)
                if ((nova = this.getPosicao(p.getLinha() + 2, (char) (p.getColuna()))) != null)
                    if (!nova.isOcupado()) disponiveis.add(nova);

            if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna() + 1))) != null)
                if (nova.getPeca()!=null && this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()))
                {
                    nova.setEnPassant(false);
                    disponiveis.add(nova);
                }

            if ((nova = this.getPosicao(p.getLinha() + 1, (char) (p.getColuna() - 1))) != null)
                if (nova.getPeca()!=null && this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()))
                {
                    nova.setEnPassant(false);
                    disponiveis.add(nova);
                }

            if((nova=this.getPosicao(p.getLinha(), (char)(p.getColuna()+1)))!=null && !pc)
                if(nova.getPeca()!=null && nova.getPeca() instanceof Peao && this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()) && ((Peao)(nova.getPeca())).isFoiPrimeiroLance())
                    if(dentroLimites(nova.getLinha()+1, (char)(nova.getColuna()+1)))
                    {
                        disponiveis.add(getPosicao(p.getLinha() + 1, (char) (p.getColuna() + 1)));
                        nova.setEnPassant(true);
                    }

            if((nova=this.getPosicao(p.getLinha(), (char)(p.getColuna()-1)))!=null  && !pc)
                if(nova.getPeca()!=null && nova.getPeca() instanceof Peao && this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()) && ((Peao)(nova.getPeca())).isFoiPrimeiroLance())
                    if(dentroLimites(nova.getLinha()+1,nova.getColuna()))
                    {
                        disponiveis.add(getPosicao(p.getLinha() + 1, (char) (p.getColuna()-1)));
                        nova.setEnPassant(true);
                    }
        }
        else
        {
            if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna()))) != null)
            {
                if (!nova.isOcupado())
                {
                    disponiveis.add(nova);
                    flag = true;
                }
            }

            if (peca.isPrimeiroLance() && flag)
                if ((nova = this.getPosicao(p.getLinha() - 2, (char) (p.getColuna()))) != null)
                    if (!nova.isOcupado()) disponiveis.add(nova);

            if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna() + 1))) != null)
                if (nova.getPeca()!=null && this.podeComer(nova.getLinha(), nova.getColuna(), p.getPeca().getJogador()))
                {
                    nova.setEnPassant(false);
                    disponiveis.add(nova);
                }

            if ((nova = this.getPosicao(p.getLinha() - 1, (char) (p.getColuna() - 1))) != null)
                if (nova.getPeca()!=null &&this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()))
                {
                    nova.setEnPassant(false);
                    disponiveis.add(nova);
                }

            if ((nova = this.getPosicao(p.getLinha(), (char) (p.getColuna() + 1))) != null  && !pc)
                if (nova.getPeca()!=null && nova.getPeca() instanceof Peao && this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()) && ((Peao)(nova.getPeca())).isFoiPrimeiroLance())
                    if (dentroLimites(nova.getLinha() - 1,nova.getColuna()))
                    {
                        disponiveis.add(getPosicao(p.getLinha() - 1, (char)(p.getColuna()+1)));
                        nova.setEnPassant(true);
                    }

            if ((nova = this.getPosicao(p.getLinha(), (char) (p.getColuna() - 1))) != null  && !pc)
                if (nova.getPeca()!=null && nova.getPeca() instanceof Peao && this.podeComer(nova.getLinha(), nova.getColuna(),  p.getPeca().getJogador()) && ((Peao)(nova.getPeca())).isFoiPrimeiroLance())
                    if (dentroLimites(nova.getLinha() - 1, (char) (nova.getColuna())))
                    {
                        disponiveis.add(getPosicao(p.getLinha() - 1, (char)(p.getColuna()-1)));
                        nova.setEnPassant(true);
                    }
        }
        return disponiveis;
    }

    public int getLinhaTorreOriginal()
    {
        if(jogadorAtual instanceof JogadorLight)
            return 1;
        else
            return 8;
    }

    public ArrayList<Posicao> rei(Peca rei, boolean pc)
    {
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao p=encontraPeca(rei), nova;

        if(p==null || rei==null)
            return  disponiveis;

        if((nova=this.getPosicao(p.getLinha(),(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova, rei.getJogador());

        if((nova=this.getPosicao(p.getLinha(),(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova, rei.getJogador());

        if((nova=this.getPosicao(p.getLinha()+1,(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova, rei.getJogador());

        if((nova=this.getPosicao(p.getLinha()+1,(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova, rei.getJogador());

        if((nova=this.getPosicao(p.getLinha()+1,(char) (p.getColuna())))!=null)
            adiciona(disponiveis, nova, rei.getJogador());

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()+1)))!=null)
            adiciona(disponiveis, nova, rei.getJogador());

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna()-1)))!=null)
            adiciona(disponiveis, nova, rei.getJogador());

        if((nova=this.getPosicao(p.getLinha()-1,(char) (p.getColuna())))!=null)
            adiciona(disponiveis, nova, rei.getJogador());

        if(((Rei)rei).isMovido() || rei.getJogador().isCheck()  && pc)
            return disponiveis;

        for(Peca pecaJogador : rei.getJogador().getPecasTabuleiro())
            if(pecaJogador instanceof Torre)
            {
                for(int i=1;i<TABULEIRO_COLUNAS && encontraPeca(pecaJogador)!=null && encontraPeca(pecaJogador).getLinha()==getLinhaTorreOriginal();i++)
                {
                    if ((char) (encontraPeca(pecaJogador).getColuna() + i) == encontraPeca(rei).getColuna())
                    {
                        disponiveis.add(getPosicao(encontraPeca(pecaJogador).getLinha(), (char) (encontraPeca((Peca) rei).getColuna() - 2)));
                        encontraPeca(pecaJogador).setRocado(true);
                    }
                    else if (isOcupado(encontraPeca(pecaJogador).getLinha(), (char) (encontraPeca(pecaJogador).getColuna() + i)))
                        break;
                }

                for(int i=1;i<TABULEIRO_COLUNAS && encontraPeca(pecaJogador)!=null && encontraPeca(pecaJogador).getLinha()==getLinhaTorreOriginal();i++)
                {
                    if ((char) (encontraPeca(pecaJogador).getColuna() - i) == encontraPeca(rei).getColuna())
                    {
                        disponiveis.add(getPosicao(encontraPeca(pecaJogador).getLinha(), (char) (encontraPeca((Peca) rei).getColuna() + 2)));
                        encontraPeca(pecaJogador).setRocado(true);
                    }
                    else if (isOcupado(encontraPeca(pecaJogador).getLinha(), (char) (encontraPeca(pecaJogador).getColuna() - i)))
                        break;
                }
            }
        return disponiveis;
    }

    public ArrayList<Posicao> diagonal(Peca peca, boolean pc)
    {
        Posicao posicao=encontraPeca(peca);
        ArrayList<Posicao> disponiveis=new ArrayList<Posicao>();
        Posicao nova;

        if(posicao==null || peca==null)
            return disponiveis;

        for(int i=1;;i++)
        {
            nova=this.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()+i));
            if(nova==null)
                break;
            if(ultima(disponiveis,nova, peca.getJogador()))
                break;
        }
        for(int i=-1;;i--)
        {
            nova=this.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()+i));
            if(nova==null)
                break;
            if(ultima(disponiveis,nova, peca.getJogador()))
                break;
        }

        for(int i=1;;i++)
        {
            nova=this.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()-i));
            if(nova==null)
                break;
            if(ultima(disponiveis,nova, peca.getJogador()))
                break;
        }

        for(int i=-1;;i--)
        {
            nova=this.getPosicao(posicao.getLinha()+i, (char) (posicao.getColuna()-i));
            if(nova==null)
                break;
            if(ultima(disponiveis,nova, peca.getJogador()))
                break;
        }

        return disponiveis;
    }

    public void movePara(Posicao posicaoOrigem, Posicao posicaoDestino, Jogador atual, Jogador adversario)
    {
        Peca peca;
        Posicao novaPosicaoTorre;
        for(Peca p : atual.getPecasTabuleiro())
            if(p instanceof Peao)
                ((Peao)p).setFoiPrimeiroLance(false);

        if(posicaoDestino.isOcupado())
        {
            adversario.addPecaMorta(posicaoDestino.getPeca());
        }
        if((peca=posicaoOrigem.getPeca()) instanceof Peao)
        {
            if((posicaoDestino.getLinha()==4  || posicaoDestino.getLinha()==5)  && ((Peao) peca).isPrimeiroLance())
                ((Peao) peca).setFoiPrimeiroLance(true);
            ((Peao) peca).setPrimeiroLance(false);
        }
        else if((peca=posicaoOrigem.getPeca()) instanceof Rei)
        {
            ((Rei) peca).setMovido(true);
        }
        else if((peca=posicaoOrigem.getPeca()) instanceof Torre)
        {
            ((Torre) peca).setMovido(true);
        }

        this.mHistorico.addJogadasJogo(
                new Jogada(
                        peca.getNomePeca(),
                        Character.toUpperCase(posicaoOrigem.getColuna()) + Integer.toString(posicaoOrigem.getLinha()),
                        Character.toUpperCase(posicaoDestino.getColuna()) + Integer.toString(posicaoDestino.getLinha())));


        peca.desenhaPeca(posicaoDestino.getImageView());
        posicaoDestino.setPeca(peca);
        posicaoOrigem.apagaPeca();

        for(Posicao posicao : tabuleiro)
        {
            if (posicao.isEnPassant())
            {
                if(posicao.getPeca()!= null && posicao.getPeca().getJogador() == jogadorAdversario)
                    adversario.addPecaMorta(posicao.getPeca());
                posicao.setEnPassant(false);
                if(posicao.getColuna()==posicaoDestino.getColuna())
                    posicao.apagaPeca();
            }
            if (posicao.isRocado())
            {
                posicao.setRocado(false);
                if (posicao.getPeca() != null && peca instanceof Rei)
                {
                    if (posicao.getColuna() - posicaoOrigem.getColuna() > 0)
                    {
                        if(posicao==getPosicao(posicaoDestino.getLinha(), (char) (posicaoDestino.getColuna()+1)))
                        {
                            novaPosicaoTorre = getPosicao(posicaoOrigem.getLinha(), (char) (posicaoOrigem.getColuna() + 1));
                            posicao.getPeca().desenhaPeca(novaPosicaoTorre.getImageView());
                            novaPosicaoTorre.setPeca(posicao.getPeca());
                            posicao.setPeca(null);
                            posicao.apagaPeca();
                        }
                    }
                    else
                    {
                        if(posicao==getPosicao(posicaoDestino.getLinha(), (char) (posicaoDestino.getColuna()-2)))
                        {
                            novaPosicaoTorre = getPosicao(posicaoOrigem.getLinha(), (char) (posicaoOrigem.getColuna() - 1));
                            posicao.getPeca().desenhaPeca(novaPosicaoTorre.getImageView());
                            novaPosicaoTorre.setPeca(posicao.getPeca());
                            posicao.setPeca(null);
                            posicao.apagaPeca();
                        }
                    }
                }
                break;
            }
        }
    }

    public Jogador getJogadorAtual() {
        return this.jogadorAtual;
    }

    public void setJogadorAtual(Jogador jogador) {
        if(jogador != null){
            this.jogadorAtual = jogador;
        }
    }

    public void trocaJogadorActual() {
        Jogador adversario=jogadorAdversario;
        jogadorAdversario=jogadorAtual;
        jogadorAtual=adversario;
    }


    public Posicao getPosicaoRei(Jogador jogador)
    {

        for(Peca peca : jogador.getPecasTabuleiro())
            if(peca instanceof Rei)
                return encontraPeca(peca);
        return null;
    }

    public Jogador getJogadorAdversarioPeca(Peca peca)
    {
        for(Jogador j : jogadores)
            if(j==peca.getJogador())
            {
                if (j == jogadorAdversario) return jogadorAtual;
                else
                    return jogadorAdversario;
            }
        return null;
    }

    private int getUltimaLinhaJogadorAtual()
    {
        if(jogadorAtual instanceof JogadorLight)
            return 8;
        else
            return 1;
    }

    public Posicao isPeaoUltimaLinha()
    {
        for(Peca peca : jogadorAtual.getPecasTabuleiro())
            if(peca instanceof Peao)
            {
                if (encontraPeca(peca).getLinha() == getUltimaLinhaJogadorAtual())
                    return encontraPeca(peca);
            }
        return null;
    }

    public Historico getHistorico() {
        return this.mHistorico;
    }

    public Jogador getOutroJogador(Jogador j)
    {
        if(j==jogadorAdversario)
            return jogadorAtual;
        else
            return jogadorAdversario;
    }

    public void desenhaPecas()
    {
        for(Posicao p: tabuleiro)
            p.desenhaPeca();
    }

    public void setView(LinearLayout ll)
    {
        for(Posicao p: tabuleiro)
            p.setView(ll);
    }
}
