package pt.isec.tiagodaniel.xadrez.States;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;
import pt.isec.tiagodaniel.xadrez.Logic.XadrezApplication;

public class EstadoEscolhePeca extends StateAdapter {

    public EstadoEscolhePeca(GameModel game) {
        super(game);
        if (getGame().getActivity().isJogoComTempo())
            getGame().getActivity().comecaTempo(getGame().getTabuleiro().getJogadorAtual());
    }

    @Override
    public IState seguinte(int linha, char coluna) {
        Peca pecaAtual;
        ArrayList<Posicao> posicoesDisponiveis;
        Posicao posicaoPeca = getGame().getTabuleiro().getPosicao(linha, coluna);

        pecaAtual = posicaoPeca.getPeca();

        if (getGame().getModoJogo() == Constantes.CRIAR_JOGO_REDE || getGame().getModoJogo() == Constantes.JUNTAR_JOGO_REDE) {
            if (((XadrezApplication) getGame().getActivity().getApplication()).getJogadorServidor() != getGame().getTabuleiro().getJogadorAtual()) {
                return this;
            }
        }

        if (pecaAtual == null)
            return this;

        if (pecaAtual.getJogador() == getGame().getTabuleiro().getJogadorAtual()) {
            posicoesDisponiveis = pecaAtual.getDisponiveis(pecaAtual.getJogador(), false);
            getGame().getActivity().resetPosicoesDisponiveisAnteriores();
            getGame().getActivity().setPosicoesJogaveis(posicoesDisponiveis);

            if (posicoesDisponiveis.size() > 0) {
                return new EstadoEscolheDestino(getGame(), posicaoPeca);
            }
        }
        return this;
    }
}
