package pt.isec.tiagodaniel.xadrez.States;

import android.content.res.Resources;
import android.widget.ImageView;

import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;

/**
 * Created by drmoreira on 10-12-2017.
 */

public class EstadoEscolhePeca extends StateAdapter {

    public EstadoEscolhePeca(GameModel game) {
        super(game);

        if(getGame().getTabuleiro().getJogadorAtual().isCheck())
        {
            getGame().getActivity().setReiCheck(getGame().getTabuleiro().getPosicaoRei(getGame().getTabuleiro().getJogadorAtual()));
        }
        else
        {
            getGame().getActivity().resetCheck();
        }

        if(getGame().getTabuleiro().getJogadorAtual().isCheck())
        {
            if (!getGame().getTabuleiro().getJogadorAtual().hasMovimentos()) {
                this.getGame().getTabuleiro().getHistorico().setVencedorJogo(
                        this.getGame().getTabuleiro().getJogadorAtual(), false);
                System.out.println("Jogo acabou perdendo");
            }
        }
        else
        {
            if (!getGame().getTabuleiro().getJogadorAtual().hasMovimentos()) {
                this.getGame().getTabuleiro().getHistorico().setVencedorJogo(
                        this.getGame().getTabuleiro().getJogadorAtual(), true);
                System.out.println("Jogo acabou empetado");
            }
        }
    }

    @Override
    public IState seguinte(int linha, char coluna) {
        Peca pecaAtual;
        ArrayList<Posicao> posicoesDisponiveis = new ArrayList<>();
        Posicao posicaoPeca=getGame().getTabuleiro().getPosicao(linha, coluna);

        pecaAtual = posicaoPeca.getPeca();

        if (pecaAtual == null)
            return this;

        if (pecaAtual.getJogador() == getGame().getTabuleiro().getJogadorAtual()) {
            posicoesDisponiveis = pecaAtual.getDisponiveis();
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
