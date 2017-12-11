package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class EstadoEscolheDestino extends StateAdapter {

    public EstadoEscolheDestino(GameModel game) {
        super(game);
    }

    @Override
    public IState seguinte() {
        //Faz o que tem a fazer

        return new EstadoEscolhePeca(this.getGame());
    }
}
