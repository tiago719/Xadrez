package pt.isec.tiagodaniel.xadrez.States;

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

        if((pecaClick=posicaoDestino.getPeca())!=null)
        {
            if (pecaClick.getJogador() == getGame().getTabuleiro().getJogadorAtual())
            {
                getGame().getActivity().resetPosicoesDisponiveisAnteriores();
                posicoesDisponiveis = pecaClick.getDisponiveis();
                getGame().getActivity().setPosicoesJogaveis(posicoesDisponiveis);
            }
            return this;
        }
        else
        {
            if(getPosicaoOrigem().getPeca().getDisponiveis().contains(posicaoDestino))
            {
                getGame().getTabuleiro().movePara(getPosicaoOrigem(), posicaoDestino);
                getGame().getActivity().resetPosicoesDisponiveisAnteriores();

                this.getGame().getTabuleiro().trocaJogadorActual();
                return new EstadoEscolhePeca(this.getGame());
            }
            else
                return this;
        }
    }
}
