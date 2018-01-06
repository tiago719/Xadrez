package pt.isec.tiagodaniel.xadrez.States;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class EstadoEscolhePeca extends StateAdapter {

    public EstadoEscolhePeca(GameModel game) {
        super(game);
        if(getGame().getActivity().isJogoComTempo())
            getGame().getActivity().comecaTempo(getGame().getTabuleiro().getJogadorAtual());
    }

    @Override
    public IState seguinte(int linha, char coluna) {
        Peca pecaAtual;
        ArrayList<Posicao> posicoesDisponiveis;
        Posicao posicaoPeca=getGame().getTabuleiro().getPosicao(linha, coluna);

        pecaAtual = posicaoPeca.getPeca();

        if(this.getGame().getTabuleiro().getJogadorAtual() == null) {
            return this;
        }

        if (pecaAtual == null)
            return this;

        if (pecaAtual.getJogador() == getGame().getTabuleiro().getJogadorAtual()) {
            posicoesDisponiveis = pecaAtual.getDisponiveis(pecaAtual.getJogador());
            getGame().getActivity().resetPosicoesDisponiveisAnteriores();
            getGame().getActivity().setPosicoesJogaveis(posicoesDisponiveis);

            if(posicoesDisponiveis.size()>0)
            {
                return new EstadoEscolheDestino(getGame(),posicaoPeca);
            }
        }
        return this;
    }
}
