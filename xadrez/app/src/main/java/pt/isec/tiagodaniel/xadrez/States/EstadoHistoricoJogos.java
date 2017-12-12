package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class EstadoHistoricoJogos extends StateAdapter {

    public EstadoHistoricoJogos(GameModel game) {
        super(game);
    }

    @Override
    public IState seguinte(Posicao posicaoPeca) {
        //Faz o que tem a fazer

        return new EstadoDetalhesJogo(this.getGame());
    }
}
