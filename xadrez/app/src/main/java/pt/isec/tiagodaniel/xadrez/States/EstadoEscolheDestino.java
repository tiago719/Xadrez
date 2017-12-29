package pt.isec.tiagodaniel.xadrez.States;

import android.app.Activity;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class EstadoEscolheDestino extends StateAdapter {

    public EstadoEscolheDestino(GameModel game, Posicao posicaoOriginal) {
        super(game);
        setPosicaoOrigem(posicaoOriginal);
    }

    @Override
    public IState seguinte(int linha, char coluna) {

        Peca pecaClick;
        ArrayList<Posicao> posicoesDisponiveis;
        Posicao posicaoDestino = getGame().getTabuleiro().getPosicao(linha, coluna);
        pecaClick=posicaoDestino.getPeca();

       if (pecaClick!= null && pecaClick.getJogador() == getGame().getTabuleiro().getJogadorAtual()) // se clicou numa nova peca da sua equipa
        {
            getGame().getActivity().resetPosicoesDisponiveisAnteriores();
            posicoesDisponiveis = pecaClick.getDisponiveis();
            getGame().getActivity().setPosicoesJogaveis(posicoesDisponiveis);
            setPosicaoOrigem(posicaoDestino);
            return this;
        }
        if(getPosicaoOrigem().getPeca().getDisponiveis().contains(posicaoDestino))// se clicou numa posicao disponivel
        {
            getGame().getTabuleiro().movePara(getPosicaoOrigem(), posicaoDestino);
            getGame().getActivity().resetPosicoesDisponiveisAnteriores();
            getGame().getTabuleiro().getJogadorAdversario().verificaCheck();
            this.getGame().getTabuleiro().trocaJogadorActual();

            return new EstadoEscolhePeca(this.getGame());
        }
        return this; // não muda de estado
    }
}
