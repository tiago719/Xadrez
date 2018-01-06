package pt.isec.tiagodaniel.xadrez.Logic;

import java.io.IOException;
import java.net.Socket;

public class SocketHandler {
    private static Socket clientSocket;

    public static synchronized Socket getClientSocket() {
        return clientSocket;
    }

    public static synchronized void setClientSocket(Socket socket) {
        SocketHandler.clientSocket = socket;
    }

    public static void closeSocket() {
        if (SocketHandler.getClientSocket() != null) {
            try {
                SocketHandler.getClientSocket().close();
            } catch (IOException ex1) {
                System.err.println("Erro ao fechar o socket. Neste caso não interessa para o utilizador");
            }
        }
    }
}