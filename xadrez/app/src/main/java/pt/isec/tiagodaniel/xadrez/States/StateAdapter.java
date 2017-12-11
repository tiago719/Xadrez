package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class StateAdapter implements IState {
    private GameModel game;

    public StateAdapter(GameModel game)
    {
        this.game = game;
    }

    public GameModel getGame()
    {
        return this.game;
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
    public IState seguinte() {
        return this;
    }
}
