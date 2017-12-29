package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.LinearLayout;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
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


    public void substituiPeao(int resultado, Posicao posicao)
    {
        tabuleiro.getJogadorAdversario().addPecaMorta(posicao.getPeca());
        posicao.apagaPeca();
        switch (resultado)
        {
            case 1:
                posicao.setPeca(new Rainha(tabuleiro,tabuleiro.getJogadorAdversario()));
                break;
            case 2:
                posicao.setPeca(new Torre(tabuleiro,tabuleiro.getJogadorAdversario()));
                break;
            case 3:
                posicao.setPeca(new Bispo(tabuleiro,tabuleiro.getJogadorAdversario()));
                break;
            case 4:
                posicao.setPeca(new Cavalo(tabuleiro,tabuleiro.getJogadorAdversario()));
                break;
        }
        tabuleiro.getJogadorAdversario().addPeca(posicao.getPeca());
        posicao.desenhaPeca();

        tabuleiro.getJogadorAtual().verificaCheck();
        if(getTabuleiro().getJogadorAtual().isCheck())
        {
            getActivity().setReiCheck(tabuleiro.getPosicaoRei(tabuleiro.getJogadorAtual()));
        }
        else
        {
            getActivity().resetCheck();
        }

        if(tabuleiro.getJogadorAtual().isCheck())
        {
            if (!tabuleiro.getJogadorAdversario().hasMovimentos())
                System.out.println("Jogo acabou perdendo");
        }
        else
        {
            if (!tabuleiro.getJogadorAtual().hasMovimentos())
                System.out.println("Jogo acabou empetado");
        }
    }

    public void verificaCheck()
    {
        getTabuleiro().getJogadorAtual().verificaCheck();
        if(getTabuleiro().getJogadorAtual().isCheck())
        {
            getActivity().setReiCheck(getTabuleiro().getPosicaoRei(getTabuleiro().getJogadorAtual()));
        }
        else
        {
            getActivity().resetCheck();
        }

        if(getTabuleiro().getJogadorAtual().isCheck())
        {
            if (!getTabuleiro().getJogadorAtual().hasMovimentos())
                System.out.println("Jogo acabou perdendo");
        }
        else
        {
            if (!getTabuleiro().getJogadorAtual().hasMovimentos())
                System.out.println("Jogo acabou empetado");
        }
    }
    //endregion

}
