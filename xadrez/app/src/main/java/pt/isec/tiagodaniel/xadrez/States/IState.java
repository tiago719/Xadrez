package pt.isec.tiagodaniel.xadrez.States;

import pt.isec.tiagodaniel.xadrez.Logic.Posicao;

/**
 * Created by drmoreira on 10-12-2017.
 */

public interface IState {
    IState configurar2Jogadores();
    IState configurarJogoServidor();
    IState configurarJogoCliente();
    IState comecarJogo();
    IState seguinte(Posicao posicaoPeca);
}
