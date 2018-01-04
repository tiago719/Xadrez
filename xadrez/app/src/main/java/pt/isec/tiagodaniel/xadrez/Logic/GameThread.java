package pt.isec.tiagodaniel.xadrez.Logic;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.isec.tiagodaniel.xadrez.Activities.JogarContraPCActivity;
import pt.isec.tiagodaniel.xadrez.Exceptions.NullSharedPreferencesException;

/**
 * Created by drmoreira on 04-01-2018.
 */

public class GameThread extends Thread implements Constantes {
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private ClientServerMessage requestMessage = null;
    private Socket gameSocket = null;
    private JogarContraPCActivity gameActivity;
    private boolean isFirstTime = false;
    private Ferramentas ferramentas;
    private int deviceType;
    private Bundle bundle;

    /**
     * Construtor da GameThread
     *
     * @param activity   actividade que chama a thread (JogarContraPCActivity)
     * @param gameSocket socket que irá ser utilizado para comunicação entre dispositivos
     */
    public GameThread(JogarContraPCActivity activity, Socket gameSocket, int deviceType) {
        this.gameActivity = activity;
        this.gameSocket = gameSocket;
        this.deviceType = deviceType;
        this.isFirstTime = true;

        try {
            this.ferramentas = new Ferramentas(this.gameActivity);
        } catch (NullSharedPreferencesException e) {
            // TODO errorDialog
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            if (this.deviceType == CLIENTE) {
                in = new ObjectInputStream(gameSocket.getInputStream());
                out = new ObjectOutputStream(gameSocket.getOutputStream());
                requestMessage = new ClientServerMessage(this.ferramentas.getSavedName());
                // TODO falta enviar foto

                out.writeUnshared(requestMessage);
                out.flush();
            }

            while (!Thread.currentThread().isInterrupted()) {
                out = new ObjectOutputStream(gameSocket.getOutputStream());
                in = new ObjectInputStream(gameSocket.getInputStream());
                requestMessage = (ClientServerMessage) in.readObject();

                if (this.isFirstTime) {
                    bundle = new Bundle();
                    bundle.putString(NOME_JOGADOR2, requestMessage.getNomeJogador());
                    bundle.putString(FOTO_JOGADOR2, "");

                    gameActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameActivity.configuraJogador2(false, bundle);
                        }
                    });

                    requestMessage.resetDados();
                    requestMessage.setNomeJogador(this.ferramentas.getSavedName());
                    this.isFirstTime = false;
                }

                // Envia resposta
                out.writeUnshared(requestMessage);
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            // TODO errorDialog
            e.printStackTrace();
        } finally {
            if (this.gameSocket != null) {
                try {
                    this.gameSocket.close();
                } catch (IOException ex1) {
                    // TODO errorDialog
                    System.err.println("[AttendTCPClientsThread]" + ex1);
                }
            }
        }
    }
}
