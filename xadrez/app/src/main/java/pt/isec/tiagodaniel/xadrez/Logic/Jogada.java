package pt.isec.tiagodaniel.xadrez.Logic;

/**
 * Created by Tiago Coutinho on 30/12/2017.
 */

public class Jogada
{
    private Posicao posicaoOriginal, posicaoDestino;
    private Jogador adversario;
    private Tabuleiro tabuleiro;

    public Jogada(Posicao posicaoOriginal, Posicao posicaoDestino, Jogador adversario, Tabuleiro tabuleiro)
    {
        this.posicaoOriginal = posicaoOriginal;
        this.posicaoDestino = posicaoDestino;
        this.adversario=adversario;
        this.tabuleiro=tabuleiro;
    }

    public Posicao getPosicaoOriginal()
    {
        return posicaoOriginal;
    }

    public void setPosicaoOriginal(Posicao posicaoOriginal)
    {
        this.posicaoOriginal = posicaoOriginal;
    }

    public Posicao getPosicaoDestino()
    {
        return posicaoDestino;
    }

    public void setPosicaoDestino(Posicao posicaoDestino)
    {
        this.posicaoDestino = posicaoDestino;
    }

    public Jogador getAdversario()
    {
        return adversario;
    }

    public void setAdversario(Jogador adversario)
    {
        this.adversario = adversario;
    }

    public Tabuleiro getTabuleiro()
    {
        return tabuleiro;
    }
}
