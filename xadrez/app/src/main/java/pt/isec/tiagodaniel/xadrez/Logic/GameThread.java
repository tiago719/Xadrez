package pt.isec.tiagodaniel.xadrez.Logic;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
import pt.isec.tiagodaniel.xadrez.Dialogs.AlertDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.OnCompleteListener;
import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.R;

public class GameThread extends Thread implements Constantes, OnCompleteListener {
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private ClientServerMessage clientServerMessage = null;
    private Socket gameSocket = null;
    private JogarContraPCActivity gameActivity;
    private boolean isFirstTime = false;
    private Ferramentas ferramentas;
    private int deviceType;
    private Bundle bundle;
    private int linhaOrigem, linhaDestino;
    private char colunaOrigem, colunaDestino;
    private ProgressDialog progressDialog;
    private Handler procMsg;

    /**
     * Construtor da GameThread
     *
     * @param activity actividade que chama a thread (JogarContraPCActivity)
     */
    public GameThread(JogarContraPCActivity activity, int deviceType, Handler handler) throws NullSharedPreferencesException {
        this.gameActivity = activity;
        this.gameSocket = SocketHandler.getClientSocket();
        this.deviceType = deviceType;
        this.isFirstTime = true;
        this.procMsg = handler;
        this.clientServerMessage = new ClientServerMessage();

        this.ferramentas = new Ferramentas(this.gameActivity);

        this.mostraProgressDialog();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                if (isFirstTime) {
                    this.configuraJogadores();
                } else {
                    in = new ObjectInputStream(SocketHandler.getClientSocket().getInputStream());
                    clientServerMessage = (ClientServerMessage) in.readObject();

                    this.linhaDestino = clientServerMessage.getLinhaDestino();
                    this.colunaDestino = clientServerMessage.getColunaDestino();

                    this.linhaOrigem = clientServerMessage.getLinhaOrigem();
                    this.colunaOrigem = clientServerMessage.getColunaOrigem();

                    gameActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameActivity.getGameModel().getTabuleiro().movePara(
                                    gameActivity.getGameModel().getTabuleiro().getPosicao(linhaOrigem, colunaOrigem),
                                    gameActivity.getGameModel().getTabuleiro().getPosicao(linhaDestino, colunaDestino),
                                    gameActivity.getGameModel().getTabuleiro().getJogadorAtual(),
                                    gameActivity.getGameModel().getTabuleiro().getJogadorAdversario());

                            gameActivity.getGameModel().verificaCheck(gameActivity.getGameModel().getTabuleiro().getJogadorAtual(),
                                    false, 0, 'a', 0, 'a', true);
                        }
                    });

                    if (this.gameActivity.getGameModel().getModoJogo() == Constantes.CRIAR_JOGO_REDE) {
                        this.gameActivity.getGameModel().getTabuleiro().trocaJogadorActual();
                    } else if (this.gameActivity.getGameModel().getModoJogo() == Constantes.JUNTAR_JOGO_REDE) {
                        this.gameActivity.getGameModel().getTabuleiro().trocaJogadorActual();
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            if (!gameActivity.isFlagAltereiModoJogo()) {
                AlertDialog alertDialog = new AlertDialog(this.gameActivity);
                alertDialog.show(this.gameActivity.getFragmentManager(), ALERT_DIALOG);
            }
        } finally {
            SocketHandler.closeSocket();
            if (gameActivity.isFlagAltereiModoJogo()) {
                return;
            }
            gameActivity.getGameModel().setModoJogo(JOGADOR_VS_COMPUTADOR);

            if (!gameActivity.isFlagSouEuAJogar()) {
                JogaPC jogaPC = new JogaPC(gameActivity.getGameModel());
                jogaPC.start();
            }

        }
    }

    private void configuraJogadores() throws IOException, ClassNotFoundException {
        if (this.deviceType == CLIENTE) {
            in = new ObjectInputStream(gameSocket.getInputStream());
            out = new ObjectOutputStream(gameSocket.getOutputStream());

            this.enviaDadosIniciais();
            this.recebeDadosIniciais();

        } else if (this.deviceType == SERVIDOR) {
            out = new ObjectOutputStream(gameSocket.getOutputStream());
            in = new ObjectInputStream(gameSocket.getInputStream());

            this.recebeDadosIniciais();
            this.enviaDadosIniciais();

            escondeProgressDialog();
        }

        this.isFirstTime = false;
    }

    @Override
    public void onComplete(int code, String tag) {
    }

    private void mostraProgressDialog() {
        this.progressDialog = new ProgressDialog(gameActivity);
        this.progressDialog.setMessage(gameActivity.getString(R.string.progress_message_espera));
        this.progressDialog.setTitle(R.string.progress_title_espera);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.show();
    }

    private void escondeProgressDialog() {
        procMsg.post(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    private void recebeDadosIniciais() throws IOException, ClassNotFoundException {
        clientServerMessage = (ClientServerMessage) in.readObject();

        bundle = new Bundle();
        bundle.putString(NOME_JOGADOR2, clientServerMessage.getNomeJogador());
        bundle.putByteArray(FOTO_JOGADOR2, clientServerMessage.getFotoJogador());

        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameActivity.configuraJogador2(false, bundle, true);
                if (clientServerMessage.isJogoComTempo()) {
                    gameActivity.configuraTempo(clientServerMessage.isJogoComTempo(), clientServerMessage.getTempoMaximo(), clientServerMessage.getTempoGanho());
                }
                escondeProgressDialog();
            }
        });
    }

    private void enviaDadosIniciais() throws IOException {
        clientServerMessage.resetDados();
        clientServerMessage.setNomeJogador(this.ferramentas.getSavedName());
        clientServerMessage.setFotoJogador(this.ferramentas.getSavedPhoto());
        clientServerMessage.setJogoComTempo(this.gameActivity.isJogoComTempo());
        clientServerMessage.setTempoGanho(this.gameActivity.getTempoGanho());
        clientServerMessage.setTempoMaximo(this.gameActivity.getTempoMaximo());

        out.writeUnshared(clientServerMessage);
        out.flush();
    }
}
