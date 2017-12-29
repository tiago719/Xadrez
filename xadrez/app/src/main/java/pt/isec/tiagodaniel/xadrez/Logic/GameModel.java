package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.LinearLayout;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
import pt.isec.tiagodaniel.xadrez.Logic.Historico.Historico;
import pt.isec.tiagodaniel.xadrez.States.EstadoEscolhePeca;
import pt.isec.tiagodaniel.xadrez.States.IState;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class GameModel {
    private Tabuleiro tabuleiro;
    private IState state;
    private JogarContraPCActivity activity;

    public GameModel(LinearLayout ll, JogarContraPCActivity activity)
    {
        this.activity=activity;

        tabuleiro = new Tabuleiro(ll);
        this.setState(new EstadoEscolhePeca(this));
    }

    public JogarContraPCActivity getActivity()
    {
        return activity;
    }

    public Tabuleiro getTabuleiro() { return this.tabuleiro;}
    public IState getState() { return this.state; }
    public void setState(IState state) { this.state = state; }

    public void seguinte(int linha, char coluna) {
        this.setState(this.state.seguinte(linha, coluna));
    }
    //endregion

}
