package pt.isec.tiagodaniel.xadrez.Logic;

import android.widget.Chronometer;
import android.widget.LinearLayout;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.States.EstadoEscolhePeca;
import pt.isec.tiagodaniel.xadrez.States.IState;

public class GameModel implements Constantes {
    private Tabuleiro tabuleiro;
    private IState state;
    private JogarContraPCActivity activity;
    private XadrezApplication xadrezApplication;
    private int modoJogo;

    public GameModel(LinearLayout ll, JogarContraPCActivity activity, Chronometer chronometer1, Chronometer chronometer2, int modoJogo) throws NullSharedPreferencesException {
        this.activity = activity;
        this.xadrezApplication = XadrezApplication.getInstance();

        this.modoJogo = modoJogo;
        tabuleiro = new Tabuleiro(ll, chronometer1, chronometer2, this.modoJogo);

        if (this.modoJogo == CRIAR_JOGO_REDE) {
            GameThread gameThread = new GameThread(this.activity, SocketHandler.getClientSocket(), Constantes.SERVIDOR);
            gameThread.start();
        } else if (this.modoJogo == JUNTAR_JOGO_REDE) {
            GameThread gameThread = new GameThread(this.activity, SocketHandler.getClientSocket(), Constantes.CLIENTE);
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

    public IState getState() {
        return this.state;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public void seguinte(int linha, char coluna) {
        this.setState(this.state.seguinte(linha, coluna));
    }

    public void substituiPeao(int resultado, Posicao posicao, Jogador atual) {
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

        verificaCheck(atual);

        if (this.getModoJogo() == JOGADOR_VS_COMPUTADOR) {
            getTabuleiro().trocaJogadorActual();
            jogaPC.start();
        } else if (this.getModoJogo() == JOGADOR_VS_JOGADOR) {
            if (getActivity().isJogoComTempo())
                getActivity().paraTempo(getTabuleiro().getJogadorAtual());

            getTabuleiro().trocaJogadorActual();
        }
    }

    //true - acabou o jogo
    //false - jogo continua
    public boolean verificaCheck(Jogador atual) {
        Jogador adversario = getTabuleiro().getOutroJogador(atual);
        adversario.verificaCheck();
        if (adversario.isCheck()) {
            getActivity().setReiCheck(getTabuleiro().getPosicaoRei(adversario));
        } else {
            getActivity().resetCheck();
        }

        if (adversario.isCheck()) {
            if (!adversario.hasMovimentos(adversario)) {
                this.getTabuleiro().getHistorico().setVencedorJogo(this.getActivity(), atual, false);
                getActivity().mostrarVencedor(atual);
                return true;
            }
        } else {
            if (!adversario.hasMovimentos(adversario)) {
                this.getTabuleiro().getHistorico().setVencedorJogo(this.getActivity(), atual, true);
                getActivity().mostrarEmpate();
                return true;
            }
        }
        return false;
    }
    //endregion

    public int getModoJogo() {
        return this.modoJogo;
    }
}
