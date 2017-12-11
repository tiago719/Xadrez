package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class EstadoInicial extends StateAdapter {

    public EstadoInicial(GameModel game) {
        super(game);
    }

    @Override
    public IState configurar2Jogadores() {
        //Faz o que tem a fazer

        return new Estado2Jogadores(this.getGame());
    }

    @Override
    public IState configurarJogoServidor() {
        //Faz o que tem a fazer

        return new EstadoJogoServidor(this.getGame());
    }

    @Override
    public IState configurarJogoCliente() {
        //Faz o que tem a fazer

        return new EstadoJogoCliente(this.getGame());
    }

    @Override
    public IState comecarJogo() {
        //Faz o que tem a fazer

        return new EstadoEscolhePeca(this.getGame());
    }
}
