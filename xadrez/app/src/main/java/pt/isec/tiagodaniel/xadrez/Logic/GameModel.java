package pt.isec.tiagodaniel.xadrez.Logic;

import pt.isec.tiagodaniel.xadrez.States.EstadoInicial;
import pt.isec.tiagodaniel.xadrez.States.IState;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class GameModel {
    private GameData gameData;
    private IState state;

    public GameModel() {
        gameData = new GameData();
        this.setState(new EstadoInicial(this));
    }

    public GameData getGameData() { return this.gameData;}
    public IState getState() { return this.state; }
    public void setState(IState state) { this.state = state; }

    //region Funções delegadas na máquina de estados
    public void configurar2Jogadores() {
        this.setState(this.state.configurar2Jogadores());
    }

    public void configurarJogoServidor() {
        this.setState(this.state.configurarJogoServidor());
    }

    public void configurarJogoCliente() {
        this.setState(this.state.configurarJogoCliente());
    }

    public void comecarJogo() {
        this.setState(this.state.comecarJogo());
    }

    public void seguinte() {
        this.setState(this.state.seguinte());
    }
    //endregion

}
