package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;

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

    public boolean jogaPC()
    {
        Posicao posicaoPeao;

        getGame().getTabuleiro().getJogadorAtual().joga();

        if(getGame().verificaCheck(getGame().getTabuleiro().getJogadorAtual()))
            return true;

        if((posicaoPeao=getGame().getTabuleiro().isPeaoUltimaLinha())!=null)
        {
            getGame().getActivity().peaoUltimaLinha(posicaoPeao, getGame().getTabuleiro().getJogadorAtual());
        }

        this.getGame().getTabuleiro().trocaJogadorActual();

        return false;
    }

    @Override
    public IState seguinte(int linha, char coluna) {
        return this;
    }
}
