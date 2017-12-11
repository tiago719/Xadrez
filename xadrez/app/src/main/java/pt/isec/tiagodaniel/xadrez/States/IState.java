package pt.isec.tiagodaniel.xadrez.States;

/**
 * Created by drmoreira on 10-12-2017.
 */

public interface IState {
    IState configurar2Jogadores();
    IState configurarJogoServidor();
    IState configurarJogoCliente();
    IState comecarJogo();
    IState seguinte();
}
