package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class EstadoEscolheDestino extends StateAdapter {

    public EstadoEscolheDestino(GameModel game) {
        super(game);
    }

    @Override
    public IState seguinte(Posicao posicaoPeca) {
        //Faz o que tem a fazer
        this.setPosicaoDestino(posicaoPeca);

        this.getGame().getTabuleiro().movePara(this.getPosicaoOrigem(), this.getPosicaoDestino());

        this.getGame().getTabuleiro().trocaJogadorActual();
        return new EstadoEscolhePeca(this.getGame());
    }
}
