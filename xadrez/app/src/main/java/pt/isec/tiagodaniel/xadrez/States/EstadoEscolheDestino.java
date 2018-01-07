package pt.isec.tiagodaniel.xadrez.States;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import pt.isec.tiagodaniel.xadrez.Dialogs.AlertDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.OnCompleteListener;
import pt.isec.tiagodaniel.xadrez.Logic.ClientServerMessage;
import pt.isec.tiagodaniel.xadrez.Logic.Constantes;
import pt.isec.tiagodaniel.xadrez.Logic.GameModel;
import pt.isec.tiagodaniel.xadrez.Logic.JogaPC;
import pt.isec.tiagodaniel.xadrez.Logic.Peca;
import pt.isec.tiagodaniel.xadrez.Logic.Posicao;
import pt.isec.tiagodaniel.xadrez.Logic.SocketHandler;

public class EstadoEscolheDestino extends StateAdapter implements Constantes, OnCompleteListener {

    public EstadoEscolheDestino(GameModel game, Posicao posicaoOriginal) {
        super(game);
        setPosicaoOrigem(posicaoOriginal);
    }

    @Override
    public IState seguinte(int linha, char coluna) {

        Peca pecaClick;
        Posicao posicaoPeao;
        ArrayList<Posicao> posicoesDisponiveis;
        Posicao posicaoDestino = getGame().getTabuleiro().getPosicao(linha, coluna);
        pecaClick = posicaoDestino.getPeca();
        boolean flag1 = true;
        JogaPC jogaPC = new JogaPC(getGame());

        if (pecaClick != null && pecaClick.getJogador() == getGame().getTabuleiro().getJogadorAtual()) // se clicou numa nova peca da sua equipa
        {
            getGame().getActivity().resetPosicoesDisponiveisAnteriores();
            posicoesDisponiveis = pecaClick.getDisponiveis(pecaClick.getJogador(), false);
            getGame().getActivity().setPosicoesJogaveis(posicoesDisponiveis);
            setPosicaoOrigem(posicaoDestino);
            return this;
        }
        if (getPosicaoOrigem().getPeca().getDisponiveis(getGame().getTabuleiro().getJogadorAtual(), false).contains(posicaoDestino))// se clicou numa posicao disponivel
        {
            getGame().getTabuleiro().movePara(getPosicaoOrigem(), posicaoDestino, getGame().getTabuleiro().getJogadorAtual(), getGame().getTabuleiro().getJogadorAdversario());
            getGame().getActivity().resetPosicoesDisponiveisAnteriores();

            if (getGame().getModoJogo() == CRIAR_JOGO_REDE || getGame().getModoJogo() == JUNTAR_JOGO_REDE) {
                if (getGame().verificaCheck(getGame().getTabuleiro().getJogadorAtual(),
                        true,
                        posicaoDestino.getLinha(),
                        posicaoDestino.getColuna(),
                        getPosicaoOrigem().getLinha(),
                        getPosicaoOrigem().getColuna(),
                        false))
                    return this;
            } else {
                if (getGame().verificaCheck(getGame().getTabuleiro().getJogadorAtual(), false, 0, 'a', 0, 'a', false))
                    return this;
            }

            if ((posicaoPeao = getGame().getTabuleiro().isPeaoUltimaLinha()) != null) {
                flag1 = false;
                getGame().getActivity().peaoUltimaLinha(posicaoPeao, getGame().getTabuleiro().getJogadorAtual());
            }

            if (flag1 && this.getGame().getModoJogo() == JOGADOR_VS_COMPUTADOR) {
                this.getGame().getTabuleiro().trocaJogadorActual();
                jogaPC.start();
            } else if (flag1 && this.getGame().getModoJogo() == JOGADOR_VS_JOGADOR) {
                if (getGame().getActivity().isJogoComTempo()) {
                    getGame().getActivity().paraTempo(getGame().getTabuleiro().getJogadorAtual());
                }
                this.getGame().getTabuleiro().trocaJogadorActual();
            } else if (flag1 && this.getGame().getModoJogo() == CRIAR_JOGO_REDE || this.getGame().getModoJogo() == JUNTAR_JOGO_REDE) {
                this.getGame().sendTCPMessage(posicaoDestino.getLinha(), posicaoDestino.getColuna(), getPosicaoOrigem().getLinha(), getPosicaoOrigem().getColuna());
                getGame().getTabuleiro().trocaJogadorActual();
            }
            return new EstadoEscolhePeca(this.getGame());
        }
        return this; // não muda de estado
    }

    @Override
    public void onComplete(int code, String tag) {
        switch (code) {
            case ERROR_OK: {
                SocketHandler.closeSocket();
                getGame().setModoJogo(JOGADOR_VS_COMPUTADOR);
                break;
            }
        }
    }
}
