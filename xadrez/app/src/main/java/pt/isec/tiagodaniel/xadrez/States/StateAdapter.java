package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
import pt.isec.tiagodaniel.xadrez.Logic.Bispo;
import pt.isec.tiagodaniel.xadrez.Logic.Cavalo;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;
import pt.isec.tiagodaniel.xadrez.Logic.Rainha;
import pt.isec.tiagodaniel.xadrez.Logic.Torre;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class StateAdapter implements IState {
    private GameModel game;
    private Posicao posicaoOrigem;
    private Posicao posicaoDestino;

    public StateAdapter(GameModel game)
    {
        this.game = game;
        posicaoOrigem=null;
        posicaoDestino=null;
    }

    public GameModel getGame()
    {
        return this.game;
    }

    public Posicao getPosicaoOrigem() {
        return posicaoOrigem;
    }

    public void setPosicaoOrigem(Posicao posicaoOrigem) {
        this.posicaoOrigem = posicaoOrigem;
    }

    public Posicao getPosicaoDestino() {
        return posicaoDestino;
    }

    public void setPosicaoDestino(Posicao posicaoDestino) {
        this.posicaoDestino = posicaoDestino;
    }

  /*  public boolean jogaPC()
    {
        Posicao posicaoPeao;

        getGame().getTabuleiro().getJogadorAtual().joga();

        if((posicaoPeao=getGame().getTabuleiro().isPeaoUltimaLinha())!=null)
        {
            getGame().getTabuleiro().getJogadorAtual().addPecaMorta(posicaoPeao.getPeca());
            posicaoPeao.apagaPeca();

            int rand=1 + (int)(Math.random() * ((10 - 1) + 1));

            if(rand<6)
                posicaoPeao.setPeca(new Rainha(getGame().getTabuleiro(),getGame().getTabuleiro().getJogadorAtual()));
            else if(rand<7)
                posicaoPeao.setPeca(new Torre(getGame().getTabuleiro(),getGame().getTabuleiro().getJogadorAtual()));
            else if(rand<8)
                posicaoPeao.setPeca(new Bispo(getGame().getTabuleiro(),getGame().getTabuleiro().getJogadorAtual()));
            else
                posicaoPeao.setPeca(new Cavalo(getGame().getTabuleiro(),getGame().getTabuleiro().getJogadorAtual()));

            getGame().getTabuleiro().getJogadorAtual().addPeca(posicaoPeao.getPeca());
            posicaoPeao.desenhaPeca();
        }

        if(getGame().verificaCheck(getGame().getTabuleiro().getJogadorAtual()))
            return true;

        this.getGame().getTabuleiro().trocaJogadorActual();

        return false;
    }*/

    @Override
    public IState seguinte(int linha, char coluna) {
        return this;
    }
}
