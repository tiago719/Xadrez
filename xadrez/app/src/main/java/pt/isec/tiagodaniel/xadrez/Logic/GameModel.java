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
    private XadrezApplication xadrezApplication;

    public GameModel(LinearLayout ll, JogarContraPCActivity activity)
    {
        this.activity=activity;
        this.xadrezApplication = ((XadrezApplication) this.activity.getApplication());

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


    public void substituiPeao(int resultado, Posicao posicao, Jogador atual)
    {
        atual.addPecaMorta(posicao.getPeca());
        posicao.apagaPeca();
        switch (resultado)
        {
            case Constantes.RAINHA_ESCOLHIDA:
                posicao.setPeca(new Rainha(tabuleiro,atual));
                break;
            case Constantes.TORRE_ESCOLHIDA:
                posicao.setPeca(new Torre(tabuleiro,atual));
                break;
            case Constantes.BISPO_ESCOLHIDA:
                posicao.setPeca(new Bispo(tabuleiro,atual));
                break;
            case Constantes.CAVALO_ESCOLHIDA:
                posicao.setPeca(new Cavalo(tabuleiro,atual));
                break;
        }
        atual.addPeca(posicao.getPeca());
        posicao.desenhaPeca();

        verificaCheck(tabuleiro.getOutroJogador(atual));

        getTabuleiro().trocaJogadorActual();

        state.jogaPC();
    }

    //true - acabou o jogo
    //false - jogo continua
    public boolean verificaCheck(Jogador atual)
    {
        Jogador adversario=getTabuleiro().getOutroJogador(atual);
        adversario.verificaCheck();
        if(adversario.isCheck())
        {
            getActivity().setReiCheck(getTabuleiro().getPosicaoRei(adversario));
        }
        else
        {
            getActivity().resetCheck();
        }

        if(adversario.isCheck())
        {
            if (!adversario.hasMovimentos()) {
                this.getTabuleiro().getHistorico().setVencedorJogo(this.getActivity(), atual, false);
                getActivity().mostrarVencedor(atual);
                return true;
            }
        }
        else
        {
            if (!adversario.hasMovimentos()) {
                this.getTabuleiro().getHistorico().setVencedorJogo(this.getActivity(), atual, true);
                getActivity().mostrarEmpate();
                return true;
            }
        }
        return false;
    }
    //endregion

    public XadrezApplication getXadrezApplication() {
        return this.xadrezApplication;
    }
}
