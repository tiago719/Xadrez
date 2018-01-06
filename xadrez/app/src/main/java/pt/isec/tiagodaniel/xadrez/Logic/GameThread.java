package pt.isec.tiagodaniel.xadrez.Logic;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
import pt.isec.tiagodaniel.xadrez.Dialogs.AlertDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.ErrorDialog;
import pt.isec.tiagodaniel.xadrez.Dialogs.OnCompleteListener;
import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;
import pt.isec.tiagodaniel.xadrez.R;

public class GameThread extends Thread implements Constantes, OnCompleteListener {
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private ClientServerMessage requestMessage = null;
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
     * @param activity   actividade que chama a thread (JogarContraPCActivity)
     * @param gameSocket socket que irá ser utilizado para comunicação entre dispositivos
     */
    public GameThread(JogarContraPCActivity activity, Socket gameSocket, int deviceType, Handler handler) throws NullSharedPreferencesException {
        this.gameActivity = activity;
        this.gameSocket = gameSocket;
        this.deviceType = deviceType;
        this.isFirstTime = true;
        this.procMsg = handler;

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
                    requestMessage = (ClientServerMessage) in.readObject();

                    this.linhaDestino = requestMessage.getLinhaDestino();
                    this.colunaDestino = requestMessage.getColunaDestino();

                    this.linhaOrigem = requestMessage.getLinhaOrigem();
                    this.colunaOrigem = requestMessage.getColunaOrigem();

                    gameActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameActivity.getGameModel().getTabuleiro().movePara(
                                    gameActivity.getGameModel().getTabuleiro().getPosicao(linhaOrigem, colunaOrigem),
                                    gameActivity.getGameModel().getTabuleiro().getPosicao(linhaDestino, colunaDestino),
                                    gameActivity.getGameModel().getTabuleiro().getJogadorAtual(),
                                    gameActivity.getGameModel().getTabuleiro().getJogadorAdversario());
                        }
                    });

                    if (this.gameActivity.getGameModel().getModoJogo() == Constantes.CRIAR_JOGO_REDE) {
                        this.gameActivity.getGameModel().getTabuleiro().setJogadorAtual(this.gameActivity.getGameModel().getTabuleiro().getJogador(1));
                    } else if (this.gameActivity.getGameModel().getModoJogo() == Constantes.JUNTAR_JOGO_REDE) {
                        this.gameActivity.getGameModel().getTabuleiro().setJogadorAtual(this.gameActivity.getGameModel().getTabuleiro().getJogador(0));
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            AlertDialog alertDialog = new AlertDialog(this.gameActivity);
            alertDialog.show(this.gameActivity.getFragmentManager(), ALERT_DIALOG);
        } finally {
            if (this.gameSocket != null) {
                try {
                    this.gameSocket.close();
                } catch (IOException ex1) {
                    System.err.println("Erro ao fechar o socket. Neste caso não interessa para o utilizador");
                }
            }
            this.gameActivity.getGameModel().setModoJogo(JOGADOR_VS_COMPUTADOR);
        }
    }

    private void configuraJogadores() throws IOException, ClassNotFoundException {
        if (this.deviceType == CLIENTE) {
            in = new ObjectInputStream(gameSocket.getInputStream());
            out = new ObjectOutputStream(gameSocket.getOutputStream());
            requestMessage = new ClientServerMessage();
            requestMessage.setNomeJogador(this.ferramentas.getSavedName());
            requestMessage.setFotoJogador(this.ferramentas.getSavedPhoto());

            out.writeUnshared(requestMessage);
            out.flush();

            requestMessage = (ClientServerMessage) in.readObject();

            bundle = new Bundle();
            bundle.putString(NOME_JOGADOR2, requestMessage.getNomeJogador());
            bundle.putByteArray(FOTO_JOGADOR2, requestMessage.getFotoJogador());

            gameActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameActivity.configuraJogador2(false, bundle, true);
                    escondeProgressDialog();
                }
            });

        } else if (this.deviceType == SERVIDOR) {
            out = new ObjectOutputStream(gameSocket.getOutputStream());
            in = new ObjectInputStream(gameSocket.getInputStream());
            requestMessage = (ClientServerMessage) in.readObject();

            bundle = new Bundle();
            bundle.putString(NOME_JOGADOR2, requestMessage.getNomeJogador());
            bundle.putByteArray(FOTO_JOGADOR2, requestMessage.getFotoJogador());

            gameActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameActivity.configuraJogador2(false, bundle, true);
                }
            });

            requestMessage.resetDados();
            requestMessage.setNomeJogador(this.ferramentas.getSavedName());
            requestMessage.setFotoJogador(this.ferramentas.getSavedPhoto());

            out.writeUnshared(requestMessage);
            out.flush();
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
}
