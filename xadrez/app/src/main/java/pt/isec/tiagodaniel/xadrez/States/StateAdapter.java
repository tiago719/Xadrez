package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
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

    @Override
    public IState configurar2Jogadores() {
        return this;
    }

    @Override
    public IState configurarJogoServidor() {
        return this;
    }

    @Override
    public IState configurarJogoCliente() {
        return this;
    }

    @Override
    public IState comecarJogo() {
        return this;
    }

    @Override
    public IState seguinte(Posicao posicaoPeca) {
        return this;
    }
}
