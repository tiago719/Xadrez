package pt.isec.tiagodaniel.xadrez.Logic;

import android.os.Handler;
import android.os.StrictMode;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.ObjectOutputStream;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
import pt.isec.tiagodaniel.xadrez.Dialogs.AlertDialog;
import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.Logic.Historico.Historico;
import pt.isec.tiagodaniel.xadrez.States.EstadoEscolhePeca;
import pt.isec.tiagodaniel.xadrez.States.IState;

public class GameModel implements Constantes {
    private Tabuleiro tabuleiro;
    private IState state;
    private JogarContraPCActivity activity;
    private XadrezApplication xadrezApplication;
    private int modoJogo;
    private ClientServerMessage messageToSend;
    private Posicao posicaoAtual;

    public GameModel(LinearLayout ll, JogarContraPCActivity activity, Chronometer chronometer1, Chronometer chronometer2, int modoJogo) throws NullSharedPreferencesException {
        messageToSend = new ClientServerMessage();
        this.activity = activity;
        this.xadrezApplication = (XadrezApplication) getActivity().getApplication();

        this.modoJogo = modoJogo;
        tabuleiro = new Tabuleiro(ll, chronometer1, chronometer2, this.modoJogo);

        if (this.modoJogo == CRIAR_JOGO_REDE) {
            this.xadrezApplication.setJogadorServidor(tabuleiro.getJogadorAtual());
            GameThread gameThread = new GameThread(this.activity, Constantes.SERVIDOR, new Handler());
            gameThread.start();
        } else if (this.modoJogo == JUNTAR_JOGO_REDE) {
            this.xadrezApplication.setJogadorServidor(tabuleiro.getJogadorAdversario());
            GameThread gameThread = new GameThread(this.activity, Constantes.CLIENTE, new Handler());
            gameThread.start();
        }

        this.setState(new EstadoEscolhePeca(this));
    }

    public JogarContraPCActivity getActivity() {
        return activity;
    }

    public Tabuleiro getTabuleiro() {
        return this.tabuleiro;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public void seguinte(int linha, char coluna) {
        this.setState(this.state.seguinte(linha, coluna));
    }

    public void substituiPeao(int resultado, Posicao posicao, Jogador atual, boolean fromThread) {
        JogaPC jogaPC = new JogaPC(this);
        atual.addPecaMorta(posicao.getPeca());
        posicao.apagaPeca();
        switch (resultado) {
            case Constantes.RAINHA_ESCOLHIDA:
                posicao.setPeca(new Rainha(tabuleiro, atual));
                break;
            case Constantes.TORRE_ESCOLHIDA:
                posicao.setPeca(new Torre(tabuleiro, atual));
                break;
            case Constantes.BISPO_ESCOLHIDA:
                posicao.setPeca(new Bispo(tabuleiro, atual));
                break;
            case Constantes.CAVALO_ESCOLHIDA:
                posicao.setPeca(new Cavalo(tabuleiro, atual));
                break;
        }
        atual.addPeca(posicao.getPeca());
        posicao.desenhaPeca();

        verificaCheck(atual, false, 0, 'a', 0, 'a', false);

        if (this.getModoJogo() == JOGADOR_VS_COMPUTADOR) {
            getTabuleiro().trocaJogadorActual();
            jogaPC.start();
        } else if (this.getModoJogo() == JOGADOR_VS_JOGADOR) {
            if (getActivity().isJogoComTempo())
                getActivity().paraTempo(getTabuleiro().getJogadorAtual(), false);
            getTabuleiro().trocaJogadorActual();
        } else if (this.getModoJogo() == JUNTAR_JOGO_REDE || this.getModoJogo() == CRIAR_JOGO_REDE) {
            if(!fromThread) {
                this.sendTCPMessage(posicao.getLinha(), posicao.getColuna(), posicaoAtual.getLinha(), posicaoAtual.getColuna(), true, resultado);
            }
            getTabuleiro().trocaJogadorActual();
        }
    }

    //true - acabou o jogo
    //false - jogo continua
    public boolean verificaCheck(Jogador atual, boolean enviarPosicoes, int lD, char cD, int lO, char cO, boolean jogoRede) {
        Jogador adversario;
        String nomeVencedor;

        if (jogoRede) {
            adversario = getTabuleiro().getJogadorAtual();
        } else {
            adversario = getTabuleiro().getOutroJogador(atual);
        }

        adversario.verificaCheck();
        if (adversario.isCheck()) {
            getActivity().setReiCheck(getTabuleiro().getPosicaoRei(adversario));
        } else {
            getActivity().resetCheck();
        }

        if (adversario.isCheck()) {
            if (!adversario.hasMovimentos(adversario)) {
                if (jogoRede) {
                    nomeVencedor = this.activity.getNomeJogador2();
                } else {
                    nomeVencedor = this.activity.getNomeJogador1();
                }

                if (enviarPosicoes) {
                    this.sendTCPMessage(lD, cD, lO, cO, false, 0);
                }

                this.tabuleiro.setVencedorJogo(nomeVencedor, atual, false);
                getActivity().mostrarVencedor(nomeVencedor);
                return true;
            }
        } else {
            if (!adversario.hasMovimentos(adversario)) {
                if (jogoRede) {
                    this.tabuleiro.setVencedorJogo(this.activity.getNomeJogador2(), atual, true);
                } else {
                    this.tabuleiro.setVencedorJogo(this.activity.getNomeJogador1(), atual, true);
                }
                if (enviarPosicoes) {
                    this.sendTCPMessage(lD, cD, lO, cO, false, 0);
                }
                getActivity().mostrarEmpate();
                return true;
            }
        }
        return false;
    }
    //endregion


    //region Funções usadas pela Activity
    public Historico getHistorico() {
        return this.tabuleiro.getHistorico();
    }
    //endregion

    public int getModoJogo() {
        return this.modoJogo;
    }

    public void setModoJogo(int modoJogo) {
        this.modoJogo = modoJogo;
        this.tabuleiro.setModoJogo(modoJogo);
    }

    public void desenhaPecas() {
        tabuleiro.desenhaPecas();
    }

    public void setView(LinearLayout ll) {
        tabuleiro.setView(ll);

    }

    public void sendTCPMessage(int linhaDestino, char colunaDestino, int linhaOrigem, char colunaOrigem, boolean flagTrocouPeao, int pecaPromovida) {
        messageToSend = new ClientServerMessage();
        messageToSend.setLinhaDestino(linhaDestino);
        messageToSend.setColunaDestino(colunaDestino);
        messageToSend.setLinhaOrigem(linhaOrigem);
        messageToSend.setColunaOrigem(colunaOrigem);
        messageToSend.setFlagTrocouPeao(flagTrocouPeao);
        messageToSend.setPecaPromivida(pecaPromovida);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    ObjectOutputStream out = new ObjectOutputStream(SocketHandler.getClientSocket().getOutputStream());

                    out.writeUnshared(messageToSend);
                    out.flush();
                } catch (IOException e) {
                    AlertDialog alertDialog = new AlertDialog(activity);
                    alertDialog.show(activity.getFragmentManager(), ALERT_DIALOG);
                }
            }
        });
    }

    public void setPosicaoAtual(Posicao posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }
}
